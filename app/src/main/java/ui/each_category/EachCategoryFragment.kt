package ui.each_category

import adapter.CategoryAdapter
import adapter.EachItemAdapter
import adapter.InsideCategoryAdapter
import adapter.SliderAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentEachCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import model.CheckInternetConnection
import model.ProduceItem


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

    private fun serRecyclerView(it: List<ProduceItem>?) {
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
        if (CheckInternetConnection().checkForInternet(requireContext())) {
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