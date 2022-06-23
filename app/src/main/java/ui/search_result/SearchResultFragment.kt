package ui.search_result

import adapter.CategoryAdapter
import adapter.InsideCategoryAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        var onSale = sharedPreferences.getBoolean("on_sale", false).toString()
        var orderBy = sharedPreferences.getString("orderBy", "id").toString()
        var maxPrice = sharedPreferences.getString("max_price", "1000000000000000").toString()

        searchResultViewModel.filter(searchValue, maxPrice, orderBy, onSale)

        setSearchResult(searchValue)
        filter()


    }

    private fun filter() {
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_searchResultFragment_to_settingFragment)
        }
    }

    private fun setSearchResult(searchValue: String) {
        searchResultViewModel.search(searchValue)
        searchResultViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager
            var adapter = InsideCategoryAdapter(this) {// id -> goToCategory(id)
            }
            adapter.submitList(it)
            binding.recyclerview.adapter = adapter
        }
    }
}