package com.example.onlineshop.ui.detail

import adapter.CommentAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.adapter.EachItemAdapter
import com.example.onlineshop.adapter.SliderAdapter
import com.example.onlineshop.data.network.NetworkParams
import com.example.onlineshop.databinding.FragmentDetailBinding
import com.example.onlineshop.model.ApiStatus
import com.example.onlineshop.model.CommentsItem
import com.example.onlineshop.model.LineItem
import com.example.onlineshop.model.OrderResponse
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

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


        detailViewModel.status.observe(viewLifecycleOwner){
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

        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        checkInternetConnection()

        detailViewModel.produceCommentsLiveData.observe(viewLifecycleOwner){
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager
            var adapter = CommentAdapter(this, { id -> })
            adapter.submitList(it)
            binding.recyclerview.adapter = adapter
            binding.recyclerview.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }

        binding.btnAddToCart.setOnClickListener {
            if (sharedPreferences.getInt("id", -1) == -1) {
                findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
            } else {
                var orderId = sharedPreferences.getInt("orderId", -1)
                Log.d("zahra", orderId.toString())
                var itemId = requireArguments().getInt("filmId", -1)

                var lineItem = LineItem(itemId, 1)
                var order = OrderResponse(3287, listOf(lineItem))

                detailViewModel.updateCart(order, orderId)
                binding.btnAddToCart.isClickable = false
                binding.btnAddToCart.text = "به سبد افزوده شد"
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
                detailViewModel.getProduceComments(it.id)
            }
        }
    }

    private fun getImageUrl(produceItem: com.example.onlineshop.model.ProduceItem) {
        for (image in produceItem.images) {
            urlList.add(
                image.src
            )
        }
    }

    private fun getCategoryList(produceItem: com.example.onlineshop.model.ProduceItem) {
        for (itemCategory in produceItem.categories) {
            category += itemCategory.name
            category += " / "
        }
    }

    private fun checkInternetConnection() {
        if (com.example.onlineshop.model.CheckInternetConnection()
                .checkForInternet(requireContext())
        ) {
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