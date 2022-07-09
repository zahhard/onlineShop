package ui.detail

import adapter.SliderAdapter
import android.content.Context
import android.content.SharedPreferences
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
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentDetailBinding
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import data.network.NetworkParams
import model.CheckInternetConnection
import model.CommentsItem
import model.ProduceItem

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    val detailViewModel: DetailViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences
    var imageCont: Int = 0
    var category: String = ""
    var comments = ArrayList<CommentsItem>()
    var urlList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        checkInternetConnection()
        binding.btnAddToCart.setOnClickListener {
            if (sharedPreferences.getInt("name", -1) == -1) {
                findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
            } else {
                var itemId = requireArguments().getInt("filmId", -1)
                NetworkParams.cartList.add(itemId)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                val gson = Gson()
                val json: String = gson.toJson(NetworkParams.cartList)
                editor.putString("cartList", json)
                editor.apply()

                binding.btnAddToCart.isClickable = false
//                binding.btnAddToCart.text = "به سبد افزوده شد"

            }
        }
    }

    private fun init() {
        var itemId = requireArguments().getInt("filmId", -1)
        detailViewModel.getItemDetail(itemId)
        observeProduceItem()
    }

    private fun observeProduceItem() {
        detailViewModel.produceItemLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvDetailName.text = it.name
                binding.tvDetailReview.text =
                    HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                imageCont = it.images.size
                getImageUrl(it)
                binding.viewPagerImageSlider.adapter =
                    SliderAdapter(this, urlList, binding.viewPagerImageSlider)
                binding.rating.text = it.average_rating.toString()
                getCategoryList(it)
                binding.tvCategoryList.text = category
                binding.detailPrice.text = it.price
            }
        }
    }

    private fun getImageUrl(produceItem: ProduceItem) {
        for (image in produceItem.images) {
            urlList.add(
                image.src
            )
        }
    }

    private fun getCategoryList(produceItem: ProduceItem) {
        for (itemCategory in produceItem.categories) {
            category += itemCategory.name
            category += " / "
        }
    }

    private fun checkInternetConnection() {
        if (CheckInternetConnection().checkForInternet(requireContext())) {
            init()
        } else
            AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage("Check your internet connection! ")
                .setPositiveButton("ok") { _, _ -> checkInternetConnection() }
                .setCancelable(false)
                .show()
    }
}