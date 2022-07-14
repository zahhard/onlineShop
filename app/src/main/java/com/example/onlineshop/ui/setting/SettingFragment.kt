package com.example.onlineshop.ui.setting

import com.example.onlineshop.adapter.FilterAdapter
import com.example.onlineshop.adapter.SizeAdapter
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.databinding.FragmentSettingBinding
import com.example.onlineshop.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel: SettingViewModel by viewModels()
    lateinit var ppreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        settingViewModel.status.observe(viewLifecycleOwner){
            if (it == Status.LOADING){
                val layout= binding.animationView
                layout.isGone = false
                binding.line1.isGone = true
            }
            else{
                val layout= binding.animationView
                layout.isGone = true
                binding.line1.isGone = false
            }
        }

        var orderBy = ""
        var maxPrice = ""
        var searchValue = ""
        var sizeList = ArrayList<String>()
        var colorList = ArrayList<String>()


        settingViewModel.getColors()
        settingViewModel.colorListLiveData.observe(viewLifecycleOwner) {
            val manager = LinearLayoutManager(requireContext())
            binding.colorRecyclerView.layoutManager = manager
            var adapter = FilterAdapter(this) { id -> colorList.add(id.toString())}
            adapter.submitList(it)
            binding.colorRecyclerView.adapter = adapter
            binding.colorRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        }

        settingViewModel.getSize()
        settingViewModel.sizeListLiveData.observe(viewLifecycleOwner) {
            val manager = LinearLayoutManager(requireContext())
            binding.sizeRecyclerView.layoutManager = manager
            var adapter = SizeAdapter(this) { id -> sizeList.add(id.toString())}
            adapter.submitList(it)
            binding.sizeRecyclerView.adapter = adapter
            binding.sizeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        }

        binding.seekbar.addOnChangeListener { rangeSlider, value, fromUser ->
            maxPrice = value.toString()
        }

        binding.btnSearch.setOnClickListener {

            orderBy = getOrderType()
            searchValue = binding.search.text.toString()
            var action =
                SettingFragmentDirections.actionSettingFragmentToSearchResultFragment(
                    colorList.toTypedArray(),
                    sizeList.toTypedArray(),
                    searchValue,
                    orderBy,
                    maxPrice
                )
            findNavController().navigate(action)
        }

    }

    private fun getOrderType(): String {
        if (binding.cheep.isChecked)
            return "cheep"
        if (binding.expensive.isChecked)
            return "price"
        if (binding.today.isChecked)
            return "date"
        return ""
    }
}