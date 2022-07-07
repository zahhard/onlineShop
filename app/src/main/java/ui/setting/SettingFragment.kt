package ui.setting

import adapter.CategoryAdapter
import adapter.FilterAdapter
import adapter.SizeAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel

@AndroidEntryPoint
class SettingFragment : Fragment() {

    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel : SettingViewModel by viewModels()
    lateinit var ppreferences: SharedPreferences
    var colorId = 0
    var sizeId = 0


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

        ppreferences = requireActivity().getSharedPreferences("search", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = ppreferences.edit()

        settingViewModel.getColors()
        settingViewModel.colorListLiveData.observe(viewLifecycleOwner){
            val manager = LinearLayoutManager(requireContext())
            binding.colorRecyclerView.layoutManager = manager
            var adapter = FilterAdapter(this) { id -> saveInBundle(id) }
            adapter.submitList(it)
            binding.colorRecyclerView.adapter = adapter
            binding.colorRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        }

        settingViewModel.getSize()
        settingViewModel.sizeListLiveData.observe(viewLifecycleOwner){
            val manager = LinearLayoutManager(requireContext())
            binding.sizeRecyclerView.layoutManager = manager
            var adapter = SizeAdapter(this) { id -> saveSize(id) }
            adapter.submitList(it)
            binding.sizeRecyclerView.adapter = adapter
            binding.sizeRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
        }

        binding.seekbar.addOnChangeListener { rangeSlider, value, fromUser ->
            editor.putString("max_price", value.toString())
            editor.apply()
        }

        binding.btnSearch.setOnClickListener {

            var orderBy =  getOrderType()
            editor.putString("orderBy", orderBy)
            editor.apply()

            findNavController().navigate(R.id.action_settingFragment_to_searchResultFragment)

            editor.putInt( "colorCount", colorId )
            editor.putInt( "sizeCount", sizeId )
            editor.putString( "search_value", binding.search.text.toString() )
            editor.apply()
        }

    }

    private fun saveSize(id: Int) {
        val editor: SharedPreferences.Editor = ppreferences.edit()
        editor.putInt("sizeId", id)
        editor.apply()
        sizeId ++
    }

    private fun saveInBundle(id: Int) {
        val editor: SharedPreferences.Editor = ppreferences.edit()
        editor.putInt("colorId", id)
        editor.apply()
        colorId ++
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