package ui.search_result

import adapter.CategoryAdapter
import adapter.InsideCategoryAdapter
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


        val onSale = requireArguments().getBoolean("on_sale", true)
        val orderBy = requireArguments().getString("orderBy", "id")
        val maxPrice = requireArguments().getString("max_price", "10000000000")
        searchResultViewModel.filter(searchResultViewModel.text.value.toString(), maxPrice, orderBy, onSale.toString())

        setSearchResult()
        filter()


    }

    private fun filter() {
        binding.filter.setOnClickListener {
            findNavController().navigate(R.id.action_searchResultFragment_to_settingFragment)
        }
    }

    private fun setSearchResult() {
        searchResultViewModel.text.observe(viewLifecycleOwner) {
            requireArguments().getString("search", "")
        }
        searchResultViewModel.search(searchResultViewModel.text.value.toString())

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