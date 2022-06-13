package ui

import adapter.CategoryAdapter
import adapter.EachItemAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import model.Category
import model.ProduceItem


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    val homeViewModel: HomeViewModel by viewModels()


    lateinit var listAdapter: ArrayAdapter<String>

    lateinit var programmingLanguagesList: ArrayList<String>;



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

        programmingLanguagesList = ArrayList()
        programmingLanguagesList.add("C")
        programmingLanguagesList.add("C#")
        programmingLanguagesList.add("Java")
        programmingLanguagesList.add("Javascript")
        programmingLanguagesList.add("Python")
        programmingLanguagesList.add("Dart")
        programmingLanguagesList.add("Kotlin")
        programmingLanguagesList.add("Typescript")

        listAdapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            programmingLanguagesList
        )

        binding.idLVProgrammingLanguages.adapter = listAdapter

        binding.idSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // on below line we are checking
                // if query exist or not.
                if (programmingLanguagesList.contains(query)) {
                    // if query exist within list we
                    // are filtering our list adapter.
                    listAdapter.filter.filter(query)
                } else {
                    // if query is not present we are displaying
                    // a toast message as no  data found..
                    Toast.makeText(requireContext(), "No Language found..", Toast.LENGTH_LONG)
                        .show()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // if query text is change in that case we
                // are filtering our adapter with
                // new text on below line.
                listAdapter.filter.filter(newText)
                return false
            }
        })

    }

    private fun setAllRecyclerViews() {
        initViewModel()

        homeViewModel.categoryListLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                callRecyclerViewCategory(it)
            }
        }

        homeViewModel.produceLiveDataPopular.observe(viewLifecycleOwner) {
            if (it != null) {
                callRecyclerViewProduceItem(it, binding.topRecyclerviewNew)
            }
        }

        homeViewModel.produceLiveDataRating.observe(viewLifecycleOwner) {
            if (it != null) {
                callRecyclerViewProduceItem(it, binding.topRecyclerviewPopular)
            }
        }

        homeViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
            if (it != null) {
                callRecyclerViewProduceItem(it, binding.topRecyclerviewBest)
            }
        }
    }

    private fun initViewModel() {
        homeViewModel.getCategoryList()
        homeViewModel.getProduceOrderByPopularity()
        homeViewModel.getProduceOrderByRating()
        homeViewModel.getProduceOrderByDate()
    }

    private fun callRecyclerViewProduceItem(it: List<ProduceItem>?, recyclerView: RecyclerView) {
        val manager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = manager
        val adapter = EachItemAdapter(this) { id -> goToDetailPage(id) }
        adapter.submitList(it)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun callRecyclerViewCategory(it: List<Category>?) {
        val manager = LinearLayoutManager(requireContext())
        binding.recyclerviewCategories.layoutManager = manager
        var adapter = CategoryAdapter(this) { goToCategory() }
        adapter.submitList(it)
        binding.recyclerviewCategories.adapter = adapter
        binding.recyclerviewCategories.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    private fun goToCategory() {
        findNavController().navigate(R.id.action_homeFragment_to_eachCategoryFragment)
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

    fun checkInternetConnection() {
        if (checkForInternet(requireContext())) {
            setAllRecyclerViews()
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("Check your internet connection! ")
                .setPositiveButton("ok") { _, _ -> checkInternetConnection() }
                .setCancelable(false)
                .show()
        }
    }
}
