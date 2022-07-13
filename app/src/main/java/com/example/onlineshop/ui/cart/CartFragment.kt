package com.example.onlineshop.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.adapter.OrderAdapter
import com.example.onlineshop.databinding.FragmentCartBinding
import com.example.onlineshop.model.ApiStatus
import com.example.onlineshop.model.LineItem
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.ProduceItem
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal
import kotlin.math.absoluteValue

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var produceList = MutableLiveData<List<ProduceItem>>(emptyList())
    private var finalIdList = ArrayList<Int>()
    private var quantityLista = ArrayList<Int>()
    private var orderId = -1
    var a = 0
    var total :BigDecimal = a.toBigDecimal()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        orderId = sharedPreferences.getInt("orderId", -1)

        ///**********************************    (  call fun  )   **********************************

        observeStatus()
        setUserName()
        callOrderRequest()
        finalPayment()
        setListOfProductId()
        getEachProduct()
        setRecyclerView()
        setListOfQuantitty()

        ///*****************************************************************************************

    }

    private fun setRecyclerView() {
        produceList.observe(viewLifecycleOwner) {
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager
            val adapter = OrderAdapter(quantityLista,"", "", this) {  id , count , price -> updateCart(id , count, price) }
            adapter.submitList(it)
            Log.d("ppprrr", it.toString())
            binding.recyclerview.adapter = adapter

            it.forEach { i ->
                total += i.price.toBigDecimal()
            }
        }
    }

    private fun updateCart(id: Int , count: Int, price :BigDecimal) {
        total += price
        var lineItem = LineItem(id , count)
        var order = OrderResponse(orderId, listOf(lineItem))
        cartViewModel.updateCart(order, orderId)
    }

    private fun getEachProduct() {
        for (productId in finalIdList) {
            cartViewModel.getItemDetail(productId)
        }

        cartViewModel.produceItemLiveData.observe(viewLifecycleOwner) {
            produceList.value?.plus(it)
            val list = produceList.value?.toMutableList()
            list?.add(it)
            produceList.value = list!!
            Log.d("ppplivedata", produceList.value.toString())
        }
    }

    private fun setListOfProductId() {
        val productIdList = sharedPreferences.getString("productIdList", "")
        var a = productIdList?.split(",")
        for (i in 0..a!!.size - 2) {
            finalIdList.add(a[i].toInt())
        }
        Log.d("pppp", finalIdList.toString())
    }

    private fun setListOfQuantitty() {
        val quantityList = sharedPreferences.getString("quantityList", "")
        var a = quantityList?.split(",")
        for (i in 0..a!!.size - 2) {
            quantityLista.add(a[i].toInt())
        }
        Log.d("pppp", quantityLista.toString())
    }

    private fun finalPayment() {
        binding.button.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Total Price : ")
                .setMessage(total.toString())
                .setNegativeButton("cancel") { _, _ -> }
                .setPositiveButton("ok") { _, _ -> clearCart() }
                .setCancelable(false)
                .show()
        }
    }

    private fun callOrderRequest() {
        cartViewModel.getMyOrder(orderId)
    }

    private fun setUserName() {
        val name = sharedPreferences.getString("name", "")
        binding.name.text = name
    }

    private fun observeStatus() {
        cartViewModel.status.observe(viewLifecycleOwner) {
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
    }

    private fun clearCart() {
        cartViewModel.deleteOrder(orderId)
        var order = OrderResponse(0, listOf())
        cartViewModel.addToCart(order)

        cartViewModel.orderLiveData.observe(viewLifecycleOwner) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putInt("orderId", it.id)
            requireContext().getSharedPreferences("login", Context.MODE_PRIVATE).edit().remove("productIdList").commit();
//            editor.putString("productIdList", "")
//            editor.putString("quantityList", "")
            editor.apply()



        }


        val manager = LinearLayoutManager(requireContext())
        binding.recyclerview.layoutManager = manager
        val adapter = OrderAdapter(quantityLista,"", "", this) {  id , count , price -> updateCart(id , count, price) }
        adapter.submitList(arrayListOf())
//        Log.d("ppprrr", it.toString())
        binding.recyclerview.adapter = adapter

        total = a.toBigDecimal()
    }
}