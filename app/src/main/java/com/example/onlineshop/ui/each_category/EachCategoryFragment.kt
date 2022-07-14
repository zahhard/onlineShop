package com.example.onlineshop.ui.each_category

import com.example.onlineshop.adapter.InsideCategoryAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentEachCategoryBinding
import com.example.onlineshop.model.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EachCategoryFragment : Fragment() {

    private lateinit var binding: FragmentEachCategoryBinding
    val eachCategoryViewModel: EachCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEachCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternetConnection()

        eachCategoryViewModel.status.observe(viewLifecycleOwner){
            if (it == Status.LOADING){
                val layout= binding.animationView
                layout.isGone = false
            }
            else{
                val layout= binding.animationView
                layout.isGone = true
            }
        }

    }

    private fun observeAll() {
        var itemId = requireArguments().getInt("categoryId", -1)
        eachCategoryViewModel.getInsideOfCategory(itemId)
        eachCategoryViewModel.produceItemLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                serRecyclerView(it)
            }
        }
    }

    private fun serRecyclerView(it: List<com.example.onlineshop.model.ProduceItem>?) {
        val manager = LinearLayoutManager(requireContext())
        binding.recyclerviewFff.layoutManager = manager
        var adapter = InsideCategoryAdapter(this) { id -> goToDetailPage(id) }
        adapter.submitList(it)
        binding.recyclerviewFff.adapter = adapter
    }

    private fun goToDetailPage(id: Int) {
        val bundle = bundleOf("filmId" to id)
        findNavController().navigate(R.id.action_eachCategoryFragment2_to_detailFragment, bundle)
    }

    private fun checkInternetConnection() {
        if (com.example.onlineshop.model.CheckInternetConnection().checkForInternet(requireContext())) {
            observeAll()
        } else
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("Check your internet connection! ")
                .setPositiveButton("ok") { _, _ -> checkInternetConnection() }
                .setCancelable(false)
                .show()
    }
}