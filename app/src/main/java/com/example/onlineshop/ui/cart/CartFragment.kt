package com.example.onlineshop.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.adapter.OrderAdapter
import com.example.onlineshop.databinding.FragmentCartBinding
import com.example.onlineshop.model.Status
import com.example.onlineshop.model.LineItem
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.ProduceItem
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var produceList = MutableLiveData<List<ProduceItem?>>(emptyList())
    private var finalIdList = ArrayList<Int>(emptyList())
    private var quantityLista = ArrayList<Int>(emptyList())
    private var orderId = -1
    var a = 0
    var total: BigDecimal = a.toBigDecimal()


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

        setListOfProductId()
        observeStatus()
        setUserName()
        callOrderRequest()
        finalPayment()

        getEachProduct()
        setRecyclerView()
        setListOfQuantitty()

        ///*****************************************************************************************

    }

    private fun setRecyclerView() {
        var d = ArrayList<ProduceItem>()
        produceList.observe(viewLifecycleOwner) {
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager
            val adapter = OrderAdapter(quantityLista, "", "", this) { id, count, price ->
                updateCart(
                    id,
                    count,
                    price
                )
            }

            adapter.submitList(it)
            binding.recyclerview.adapter = adapter


            it.forEach { i ->
                if (i != null) {
                    total += i.price.toBigDecimal()
                } else
                    total = a.toBigDecimal()

            }
        }
    }

    private fun updateCart(id: Int, count: Int, price: BigDecimal) {
        total += price
        var lineItem = LineItem(id, count)
        var order = OrderResponse(orderId, listOf(lineItem))
        cartViewModel.updateCart(order, orderId)
    }

    private fun getEachProduct() {
        for (productId in finalIdList) {
//            Log.d("eeel", productId.toString())
            cartViewModel.getItemDetail(productId)
        }

        cartViewModel.produceItemLiveData.observe(viewLifecycleOwner) {
            produceList.value?.plus(it)
            val list = produceList.value?.toMutableList()
            list?.add(it)
            produceList.value = list!!
        }
    }

    private fun setListOfProductId() {
        val productIdList = sharedPreferences.getString("productIdList", "")
        var a = productIdList?.split(",")
        for (i in 0..a!!.size - 2) {
            finalIdList.add(a[i].toInt())
        }
//        Log.d("pppp", finalIdList.toString())
    }

    private fun setListOfQuantitty() {
        val quantityList = sharedPreferences.getString("quantityList", "")
        var a = quantityList?.split(",")
        for (i in 0..a!!.size - 2) {
            quantityLista.add(a[i].toInt())
        }
//        Log.d("pppp", quantityLista.toString())
    }

    private fun finalPayment() {
        binding.button.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_cart, null)

            val btnClose = view.findViewById<Button>(R.id.algo_button)
            val btnSubmit = view.findViewById<Button>(R.id.course_button)
            val codeTextTV = view.findViewById<EditText>(R.id.editTextCode)
            val price = view.findViewById<TextView>(R.id.price)

            price.text = total.toString()

            btnClose.setOnClickListener {
                dialog.dismiss()
            }

            var temp = total
            codeTextTV.doOnTextChanged { text, start, before, count ->

                if (codeTextTV.text.toString() == "code10") {
                    total *= 0.9.toBigDecimal()
                    price.text = total.toString()
                } else {
                    total = temp
                    price.text = total.toString()
                    Log.d("lll", temp.toString())
                }
            }

            btnSubmit.setOnClickListener {
                clearCart()
                dialog.dismiss()
                Toast.makeText(requireContext(), "ثبت شد :)", Toast.LENGTH_SHORT).show()
            }


            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
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
    }

    private fun clearCart() {
        cartViewModel.deleteOrder(orderId)
        var order = OrderResponse(0, listOf())
        cartViewModel.addToCart(order)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.remove("productIdList").apply()

        Log.d("qq", sharedPreferences.getString("productIdList", "")!!)

        cartViewModel.orderLiveData.observe(viewLifecycleOwner) {
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            var a = editor.putInt("orderId", it!!.id)
            Log.d("qqr", a.toString())
            editor.apply()
        }

        var rr = sharedPreferences.getInt("orderId", -1)
        Log.d("qqr", rr.toString())




        produceList.value = emptyList()
        finalIdList.clear()
        Log.d("ttt", produceList.value.toString())
        total = a.toBigDecimal()
        quantityLista = ArrayList<Int>(emptyList())
    }
}