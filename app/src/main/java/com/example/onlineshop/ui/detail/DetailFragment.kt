package com.example.onlineshop.ui.detail

import adapter.CommentAdapter
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.adapter.SliderAdapter
import com.example.onlineshop.databinding.FragmentDetailBinding
import com.example.onlineshop.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


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

        var commentText = ""
        var commentRate = ""
        var itemId = requireArguments().getInt("filmId", -1)
        var adapter = CommentAdapter(this, { id -> })

        binding.addComment.setOnClickListener {

            // adding on click listener for our button.

            // on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(requireContext())

            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

            // on below line we are creating a variable for our button
            // which we are using to dismiss our dialog.
            val btnClose = view.findViewById<Button>(R.id.algo_button)
            val btnSubmit = view.findViewById<Button>(R.id.course_button)
            val commentTextTV = view.findViewById<EditText>(R.id.editTextCpmmentText)
            val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

            // on below line we are adding on click listener
            // for our dismissing the dialog button.
            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            btnSubmit.setOnClickListener {
                commentText = commentTextTV.text.toString()
                commentRate = ratingBar.rating.toString()
                dialog.dismiss()


                val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                if (sharedPreferences.getInt("id", -1) == -1) {
                    findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
                } else {
                    var name = sharedPreferences.getString("name", "")
                    var email = sharedPreferences.getString("email", "")
                    var commentsItem = CommentSent(itemId, commentText , name!! ,email!!, commentRate)
                    detailViewModel.postComment(commentsItem)

                    detailViewModel.commentLiveData.observe(viewLifecycleOwner){
                        detailViewModel.produceCommentsLiveData.value!!.plus(it)
//                        adapter.submitList(comments)
                    }

                    Toast.makeText(requireContext(), "ثبت شد :)", Toast.LENGTH_SHORT).show()
                    Log.d("sss", commentText + " " + commentRate)
                }
            }

            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }

        detailViewModel.status.observe(viewLifecycleOwner) {
            if (it == ApiStatus.LOADING) {
                val layout = binding.animationView
                layout.isGone = false
                binding.line1.isGone = true
            } else {
                val layout = binding.animationView
                layout.isGone = true
                binding.line1.isGone = false
            }
        }

        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        checkInternetConnection()


        detailViewModel.produceCommentsLiveData.observe(viewLifecycleOwner) {
            for (comment in it){
                comments.add(comment)
            }
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager

            adapter.submitList(comments)
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


                var lineItem = LineItem(itemId, 1)
                var order = OrderResponse(orderId, listOf(lineItem))

                detailViewModel.updateCart(order, orderId)
                binding.btnAddToCart.isClickable = false
                binding.btnAddToCart.text = "به سبد افزوده شد"


                var prev = sharedPreferences.getString("productIdList", "")
                sharedPreferences.edit().putString("productIdList", "$prev$itemId,").apply()

                var a = sharedPreferences.getString("quantityList", "")
                sharedPreferences.edit().putString("quantityList", "$a$itemId,").apply()


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