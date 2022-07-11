package com.example.onlineshop.ui.search_result

import com.example.onlineshop.adapter.CategoryAdapter
import com.example.onlineshop.adapter.InsideCategoryAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.databinding.FragmentSearchResultBinding
import com.example.onlineshop.model.ApiStatus
import dagger.hilt.android.AndroidEntryPoint
import com.example.onlineshop.ui.home.HomeViewModel
import io.supercharge.shimmerlayout.ShimmerLayout

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

        searchResultViewModel.status.observe(viewLifecycleOwner){
            if (it == ApiStatus.LOADING){
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

        var orderBy = args.ordderBy
        var maxPrice = args.maxPrice
        var sizeList = args.sizeList
        var colorList = args.colorList
        var searchValue = args.searchValue


        searchResultViewModel.filter(searchValue, maxPrice, orderBy,"color", colorList.toList())

        Log.d("aaa", searchValue + " " + orderBy)

        searchResultViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                binding.tvNotFind.isGone = false
            }
            else{
                val manager = LinearLayoutManager(requireContext())
                binding.recyclerview.layoutManager = manager
                var adapter = InsideCategoryAdapter(this) {}
                adapter.submitList(it)
                binding.recyclerview.adapter = adapter
                binding.tvNotFind.isGone = true
            }
        }
        setSearchResult(searchValue)
    }

    private fun setSearchResult(searchValue: String) {
    }
}