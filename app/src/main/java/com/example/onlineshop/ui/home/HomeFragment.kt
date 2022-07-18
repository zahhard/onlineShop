package ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.onlineshop.R
import com.example.onlineshop.adapter.CategoryAdapter
import com.example.onlineshop.adapter.EachItemAdapter
import com.example.onlineshop.adapter.SliderAdapter
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.model.Category
import com.example.onlineshop.model.CheckInternetConnection
import com.example.onlineshop.model.ProduceItem
import com.example.onlineshop.model.Status
import com.example.onlineshop.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    private var listOfImages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkInternetConnection()
        observeStatus()


    }

    private fun observeStatus() {
        homeViewModel.status.observe(viewLifecycleOwner) {
            if (it == Status.LOADING) {
                val layout = binding.decelerate
                layout.startShimmer()
            } else {
                val layout = binding.decelerate
                layout.stopShimmer()
                binding.decelerate.isGone = true
            }
        }
    }

    private fun observeSpecialProduce() {
        homeViewModel.getItemDetail()

        val pagerPadding = 16
        binding.viewPagerImageSlider.setClipToPadding(false)
        binding.viewPagerImageSlider.setPadding(pagerPadding, 0, pagerPadding, 0)

        homeViewModel.specialProduceLiveData.observe(viewLifecycleOwner) {
            for (image in it!!.images) {
                listOfImages.add(image.src)
            }
            binding.viewPagerImageSlider.adapter =
                SliderAdapter(this, listOfImages, binding.viewPagerImageSlider)
            binding.viewPagerImageSlider.clipToPadding = false
            binding.viewPagerImageSlider.clipChildren = false
            binding.viewPagerImageSlider.offscreenPageLimit = 3
            binding.viewPagerImageSlider.getChildAt(0).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
            var compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(MarginPageTransformer(40))
        }
    }

    private fun observreAllLiveDatas() {
        homeViewModel.getCategoryList()
        homeViewModel.getProduceOrderByPopularity()
        homeViewModel.getProduceOrderByRating()
        homeViewModel.getProduceOrderByDate()

        homeViewModel.categoryListLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                setCategoryRecyclerView(it)
            }
        }
        homeViewModel.produceLiveDataPopular.observe(viewLifecycleOwner) {
            if (it != null) {
                setRecyclerView(it, binding.topRecyclerviewPopular, "#b8e1ff")
            }
        }
        homeViewModel.produceLiveDataRating.observe(viewLifecycleOwner) {
            if (it != null) {
                setRecyclerView(it, binding.topRecyclerviewBest, "#a3ffd6")
            }
        }
        homeViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
            if (it != null) {
                setRecyclerView(it, binding.topRecyclerviewNew, "#e2c4f2")
            }
        }
    }

    private fun setCategoryRecyclerView(it: List<Category>?) {
        val manager = LinearLayoutManager(requireContext())
        binding.recyclerviewCategories.setLayoutManager(manager)
        var adapter = CategoryAdapter(this) { id -> goToCategory(id) }
        adapter.submitList(it)
        binding.recyclerviewCategories.setAdapter(adapter)
        binding.recyclerviewCategories.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun setRecyclerView(it: List<ProduceItem>?, recyclerView: RecyclerView, color: String) {
        val manager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = manager
        var adapter = EachItemAdapter(this, { id -> goToDetailPage(id) }, color)
        adapter.submitList(it)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun goToCategory(id: Int) {
        val bundle = bundleOf("categoryId" to id)
        findNavController().navigate(R.id.action_homeFragment_to_eachCategoryFragment2, bundle)
    }

    private fun goToDetailPage(id: Int) {
        val bundle = bundleOf("filmId" to id)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    private fun checkInternetConnection() {
        if (CheckInternetConnection().checkForInternet(requireContext())) {
            observreAllLiveDatas()
            observeSpecialProduce()
            showAll()
        } else
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("Check your internet connection! ")
                .setPositiveButton("ok") { _, _ -> checkInternetConnection() }
                .setCancelable(false)
                .show()
    }

    private fun showAll() {
        binding.showAll1.setOnClickListener {
            goToShowAll("date")
        }
        binding.showAll2.setOnClickListener {
            goToShowAll("popularity")
        }
        binding.showAll3.setOnClickListener {
            goToShowAll("rating")
        }
    }

    private fun goToShowAll(orderBy: String) {
        var bundle = bundleOf("orderBy" to orderBy)
        findNavController().navigate(R.id.action_homeFragment_to_searchResultFragment, bundle)
    }
}
