package ui

import adapter.CategoryAdapter
import adapter.EachItemAdapter
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.home.HomeViewModel


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    val homeViewModel : HomeViewModel by viewModels()

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

        if (checkForInternet(requireContext())) {
            homeViewModel.getCategoryList()
            homeViewModel.getProduceOrderByPopularity()
            homeViewModel.getProduceOrderByRating()
            homeViewModel.getProduceOrderByDate()

            homeViewModel.categoryListLiveData.observe(viewLifecycleOwner) {
                if (it!= null){
                    val manager = LinearLayoutManager(requireContext())
                    binding.recyclerviewCategories.setLayoutManager(manager)
                    var adapter = CategoryAdapter(this) { id -> goToCategory(id) }
                    adapter.submitList(it)
                    binding.recyclerviewCategories.setAdapter(adapter)
                    binding.recyclerviewCategories.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false)
                }
            }

            homeViewModel.produceLiveDataPopular.observe(viewLifecycleOwner) {
                if (it!= null){
                    val manager = LinearLayoutManager(requireContext())
                    binding.topRecyclerviewPopular.setLayoutManager(manager)

                    var adapter = EachItemAdapter(this) {  id -> goToDetailPage(id) }
                    adapter.submitList(it)
                    binding.topRecyclerviewPopular.setAdapter(adapter)
                    binding.topRecyclerviewPopular.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false)
                }
            }

            homeViewModel.produceLiveDataRating.observe(viewLifecycleOwner) {
                if (it!= null){
                    val manager = LinearLayoutManager(requireContext())
                    binding.topRecyclerviewBest.setLayoutManager(manager)

                    var adapter = EachItemAdapter(this) {  id -> goToDetailPage(id) }
                    adapter.submitList(it)
                    binding.topRecyclerviewBest.setAdapter(adapter)
                    binding.topRecyclerviewBest.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false)
                }
            }

            homeViewModel.produceLiveDataNew.observe(viewLifecycleOwner) {
                if (it!= null){
                    val manager = LinearLayoutManager(requireContext())
                    binding.topRecyclerviewNew.setLayoutManager(manager)

                    var adapter = EachItemAdapter(this) { id -> goToDetailPage(id) }
                    adapter.submitList(it)
                    binding.topRecyclerviewNew.setAdapter(adapter)
                    binding.topRecyclerviewNew.layoutManager = LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL, false)
                }
            }
        } else {
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("Check your internet connection! ")
                .setPositiveButton("ok") { _, _ -> }
                .setCancelable(false)
                .show()
        }


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

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
