package ui

import adapter.CategoryAdapter
import adapter.EachItemAdapter
import adapter.SliderAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import model.Category
import model.ProduceItem
import ui.home.HomeViewModel
import java.text.FieldPosition


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

        observeSpecialProduce()
        checkInternetConnection()
        search()
        
    }

    private fun search() {
        binding.searchButton.setOnClickListener {
            if (binding.search.text != null) {
                val bundle = bundleOf("search" to binding.search.text.toString())
                findNavController().navigate(
                    R.id.action_homeFragment_to_searchResultFragment,
                    bundle
                )
            }
        }
    }

    private fun observeSpecialProduce() {
        homeViewModel.getItemDetail()
        homeViewModel.specialProduceLiveData.observe(viewLifecycleOwner) {
            for (image in it.images) {
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
                setRecyclerView(it, binding.topRecyclerviewPopular)
            }
        }
        homeViewModel.produceLiveDataRating.observe(viewLifecycleOwner) {
            if (it != null) {
                setRecyclerView(it, binding.topRecyclerviewBest)
            }
        }
        homeViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
            if (it != null) {
                setRecyclerView(it, binding.topRecyclerviewNew)
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

    private fun setRecyclerView(it: List<ProduceItem>?, recyclerView: RecyclerView) {
        val manager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = manager
        var adapter = EachItemAdapter(this) { id -> goToDetailPage(id) }
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

    private fun checkForInternet(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun checkInternetConnection(){
        if (checkForInternet(requireContext())) {
            observreAllLiveDatas()
        }
        else
        AlertDialog.Builder(requireContext())
            .setTitle("Error")
            .setMessage("Check your internet connection! ")
            .setPositiveButton("ok") { _, _ -> checkInternetConnection() }
            .setCancelable(false)
            .show()
    }
}
