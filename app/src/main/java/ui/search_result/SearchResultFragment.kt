package ui.search_result

import adapter.CategoryAdapter
import adapter.InsideCategoryAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.home.HomeViewModel

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private val searchResultViewModel: SearchResultViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        sharedPreferences = requireActivity().getSharedPreferences("search", Context.MODE_PRIVATE)
        var searchValue = sharedPreferences.getString("search_value", "").toString()
        var maxPrice = sharedPreferences.getString("max_price", "10000000").toString()
        var id = sharedPreferences.getInt("colorId", -1)
        var sizeId = sharedPreferences.getInt("sizeId", -1)
        var orderBy = sharedPreferences.getString("orderBy", "")

        Log.d("aaa", id.toString() + " " + sizeId.toString())

        if (id != -1)
            searchResultViewModel.filter(searchValue, maxPrice, orderBy!!,"color", listOf(id.toString()))
        if (sizeId != -1)
            searchResultViewModel.filter(searchValue, maxPrice, orderBy!!,"size", listOf(sizeId.toString()))

        searchResultViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
            if (it.isEmpty()){
                AlertDialog.Builder(requireContext())
                    .setTitle("Error")
                    .setMessage("Check your internet connection! ")
                    .setPositiveButton("ok") { _, _ -> }
                    .setCancelable(false)
                    .show()

            }
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager
            var adapter = InsideCategoryAdapter(this) {// id -> goToCategory(id)
            }
            adapter.submitList(it)
            binding.recyclerview.adapter = adapter
        }


        setSearchResult(searchValue)
        filter()


    }

    private fun filter() {
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_searchResultFragment_to_settingFragment)
        }
    }

    private fun setSearchResult(searchValue: String) {
//        searchResultViewModel.search(searchValue)

    }
}