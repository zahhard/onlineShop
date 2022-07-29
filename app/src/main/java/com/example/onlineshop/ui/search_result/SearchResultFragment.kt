package com.example.onlineshop.ui.search_result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.adapter.InsideCategoryAdapter
import com.example.onlineshop.databinding.FragmentSearchResultBinding
import com.example.onlineshop.model.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private val searchResultViewModel: SearchResultViewModel by viewModels()
    private val args : SearchResultFragmentArgs by navArgs()

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

        val orderByFromHome = requireArguments().getString("orderBy", "")

        if (orderByFromHome != ""){
            searchResultViewModel.getProduceOrderBy(orderByFromHome)
        }
        else{
            val orderBy = args.ordderBy
            val maxPrice = args.maxPrice
            var sizeList = args.sizeList
            val colorList = args.colorList
            val searchValue = args.searchValue

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
                val adapter = InsideCategoryAdapter(this) { id -> goToDetail(id)}
                adapter.submitList(it)
                binding.recyclerview.adapter = adapter
                binding.tvNotFind.isGone = true
            }
        }
    }

    private fun goToDetail(id: Int) {
        val bundle = bundleOf("filmId" to id)
        findNavController().navigate(R.id.action_searchResultFragment_to_detailFragment , bundle)
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