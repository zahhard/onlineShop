package com.example.onlineshop.ui.search_result

import com.example.onlineshop.adapter.InsideCategoryAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.databinding.FragmentSearchResultBinding
import com.example.onlineshop.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private val searchResultViewModel: SearchResultViewModel by viewModels()
    val args : SearchResultFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        binding.decelerate
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeStatus()

        var orderByFromHome = requireArguments().getString("orderBy", "")

        if (orderByFromHome != ""){
            searchResultViewModel.getProduceOrderBy(orderByFromHome)
        }
        else{
            var orderBy = args.ordderBy
            var maxPrice = args.maxPrice
            var sizeList = args.sizeList
            var colorList = args.colorList
            var searchValue = args.searchValue

            searchResultViewModel.filter(searchValue, maxPrice, orderBy,"color", colorList.toList())
        }

        observeSearchResult()
    }

    private fun observeSearchResult() {
        searchResultViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
            if (it!!.isEmpty()) {
                binding.tvNotFind.isGone = false
            } else {
                val manager = LinearLayoutManager(requireContext())
                binding.recyclerview.layoutManager = manager
                var adapter = InsideCategoryAdapter(this) {}
                adapter.submitList(it)
                binding.recyclerview.adapter = adapter
                binding.tvNotFind.isGone = true
            }
        }
    }

    private fun observeStatus() {
        searchResultViewModel.status.observe(viewLifecycleOwner) {
            if (it == Status.LOADING) {
                val layout = binding.animationView
                layout.isGone = false
                binding.line1.isGone = true
            } else {
                val layout = binding.animationView
                layout.isGone = true
                binding.line1.isGone = false
            }
        }
    }
}