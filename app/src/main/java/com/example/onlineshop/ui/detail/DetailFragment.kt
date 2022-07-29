package com.example.onlineshop.ui.detail

import adapter.CommentAdapter
import android.content.Context
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
import androidx.viewpager2.widget.ViewPager2
import com.example.onlineshop.R
import com.example.onlineshop.adapter.SliderAdapter
import com.example.onlineshop.databinding.FragmentDetailBinding
import com.example.onlineshop.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
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
    var nameCart = ""
    var imageCart = ""
    var priceCart = ""
    var itemId = 0

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
        detailViewModel.count.value = sharedPreferences.getInt("count", -1)
        var commentText = ""
        var commentRate = ""
        itemId = requireArguments().getInt("filmId", -1)
        val adapter = CommentAdapter(sharedPreferences.getString("email", "")!! , this) { type, id, commentText, rating ->
            if (type == "remove"){
                detailViewModel.deleteComment(id)
                detailViewModel.getProduceComments(itemId)
            }else if ( type == "edit"){
//                detailViewModel.editComment(id)
                showDialog(type,   HtmlCompat.fromHtml(commentText, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(), rating, id)
            }
        }

        detailViewModel.count.observe(viewLifecycleOwner){
            if (it == -1)
                binding.tvCount.text = "0"
            else
                binding.tvCount.text = it.toString()
        }

        binding.cart.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_cartFragment)
        }

        binding.addComment.setOnClickListener {
            showDialog("", commentText, commentRate, itemId)
        }

        detailViewModel.status.observe(viewLifecycleOwner) {
            if (it == Status.LOADING) {
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
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager
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
                val orderId = sharedPreferences.getInt("orderId", -1)


                val lineItem = LineItem(itemId, 1)
                val order = OrderResponse(orderId, listOf(lineItem))

                var cartProduct = CartProduct(itemId, nameCart, imageCart, 1, priceCart, priceCart)
                detailViewModel.insertProductToCart(cartProduct)
//                detailViewModel.updateCart(order, orderId)
                binding.btnAddToCart.text = "به سبد افزوده شد"
                binding.btnAddToCart.isEnabled = false


                val count = sharedPreferences.getInt("count", -1) + 1

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putInt("count", count)
                editor.apply()
                Log.d("rr", sharedPreferences.getInt("count", -1).toString())
                detailViewModel.count.value = count
            }
        }
    }

    private fun showDialog(type: String, commentText: String, commentRate: String, id: Int) {
        var commentText1 = commentText
        var commentRate1 = commentRate

        val dialog = BottomSheetDialog(requireContext())

        val view = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        val btnClose = view.findViewById<Button>(R.id.algo_button)
        val btnSubmit = view.findViewById<Button>(R.id.course_button)
        val commentTextTV = view.findViewById<EditText>(R.id.editTextCpmmentText)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)


      if (commentRate1 != "")
            ratingBar.rating = commentRate1.toFloat()


        commentTextTV.setText(commentText1)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnSubmit.setOnClickListener {
            commentText1 = commentTextTV.text.toString()
            commentRate1 = ratingBar.rating.toString()
            dialog.dismiss()



            if (sharedPreferences.getInt("id", -1) == -1) {
                findNavController().navigate(R.id.action_detailFragment_to_loginFragment)
            } else {
                val name = sharedPreferences.getString("name", "")
                val email = sharedPreferences.getString("email", "")



                if (type == "edit"){
                    val commentsItem = CommentEdit(id , commentText1, name!!, email!!, commentRate1)
                    detailViewModel.editComment(id , commentsItem)
                    Toast.makeText(requireContext(), "ثبت شد. \n نیازمند زمان بیشتر است", Toast.LENGTH_SHORT).show()

                }
                else {
                    val commentsItem = CommentSent(itemId , commentText1, name!!, email!!, commentRate1)
                    detailViewModel.postComment(commentsItem)
                    Toast.makeText(requireContext(), "ثبت شد. \n نیازمند زمان بیشتر است", Toast.LENGTH_SHORT).show()
                }
                detailViewModel.commentLiveData.observe(viewLifecycleOwner) {
                    detailViewModel.produceCommentsLiveData.value?.plus(it)
//                            adapter.submitList(comments)
                }



            }
        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun init() {
        val itemId = requireArguments().getInt("filmId", -1)
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
                nameCart = it.name
                imageCart = urlList[0]
                priceCart = it.price
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

    private fun getImageUrl(produceItem:  ProduceItem) {
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
        if (CheckInternetConnection()
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