package com.example.onlineshop.ui.cart

import android.content.Context
import android.content.SharedPreferences
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.R
import com.example.onlineshop.adapter.OrderAdapter
import com.example.onlineshop.databinding.FragmentCartBinding
import com.example.onlineshop.model.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal


@AndroidEntryPoint
class CartFragment : Fragment() {
    val locationPermissionCode = 1001

    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)
        orderId = sharedPreferences.getInt("orderId", -1)

        setUserName()
        finalPayment()
        setRecyclerView()





        binding.themeMode.setOnClickListener {
            requireContext().theme.changingConfigurations
            chooseThemeDialog()
        }

        binding.map.setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_mapsFragment)
        }


        binding.ddprice.text = total.toString()
    }

    private fun setRecyclerView() {
        cartViewModel.getAllCartProducts().observe(viewLifecycleOwner) {

            if (it != null) {
                val manager = LinearLayoutManager(requireContext())
                binding.recyclerview.layoutManager = manager
                val adapter = OrderAdapter(quantityLista, "", "", this) { id, count, price ->
                    if (count == 0) {
                        cartViewModel.deleteProduct(id)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        val countt = sharedPreferences.getInt("count", -1) - 1
                        editor.putInt("count", countt)
                        editor.apply()
                    }
                    else{
                        total += price
                        binding.ddprice.text = total.toString()
                    }
                }
                adapter.submitList(it)
                calcTotalPrice(it)
                binding.recyclerview.adapter = adapter
            } else
                Toast.makeText(requireContext(), " سبد شما خالی است :( ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun calcTotalPrice(it: List<CartProduct>) {
        it.forEach { i ->
            total += i.price.toBigDecimal()
            binding.ddprice.text = total.toString()
        }
    }


    private fun chooseThemeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose theme")
        val styles = arrayOf("Light", "Dark", "System default")
        val checkedItem = 0

        builder.setSingleChoiceItems(styles, checkedItem) { dialog, which ->
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            when (which) {
                0 -> {
                    dialog.dismiss()
                    editor.putString("theme", "light")
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

                }
                1 -> {
                    editor.putString("theme", "night")
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    dialog.dismiss()

                }
                2 -> {
                    dialog.dismiss()
                    editor.putString("theme", "system")
                    editor.apply()
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

                }

            }
        }

        val dialog = builder.create()
        dialog.show()
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
                    Toast.makeText(requireContext(), " کد تخفیف اعمال شد ", Toast.LENGTH_SHORT).show()
                } else {
                    total = temp
                    price.text = total.toString()
                    Log.d("lll", temp.toString())
                }
            }

            btnSubmit.setOnClickListener {
//                clearCart()
                dialog.dismiss()
                Toast.makeText(requireContext(), "ثبت شد :)", Toast.LENGTH_SHORT).show()
            }
            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()
        }
    }
//
//    private fun callOrderRequest() {
//        cartViewModel.getMyOrder(orderId)
//    }

    private fun setUserName() {
        val name = sharedPreferences.getString("name", "")
        binding.name.text = name
    }
//
//    private fun observeStatus() {
//        cartViewModel.status.observe(viewLifecycleOwner) {
//            val layout = binding.animationView
//            layout.isGone = true
//            binding.line1.isGone = false
////            if (it == Status.LOADING) {
////                val layout = binding.animationView
////                layout.isGone = true
////                binding.line1.isGone = true
////            } else {
////                val layout = binding.animationView
////                layout.isGone = true
////                binding.line1.isGone = false
////            }
//        }
//    }

//    private fun clearCart() {
//        cartViewModel.deleteOrder(orderId)
//        var order = OrderResponse(0, listOf())
//        cartViewModel.addToCart(order)
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        editor.remove("productIdList").apply()
//
//        Log.d("qq", sharedPreferences.getString("productIdList", "")!!)
//
//        cartViewModel.orderLiveData.observe(viewLifecycleOwner) {
//            val editor: SharedPreferences.Editor = sharedPreferences.edit()
//            var a = editor.putInt("orderId", it!!.id)
//            Log.d("qqr", a.toString())
//            editor.apply()
//        }
//
//        var rr = sharedPreferences.getInt("orderId", -1)
//        Log.d("qqr", rr.toString())
//
//
//
//
//        produceList.value = emptyList()
//        finalIdList.clear()
//        Log.d("ttt", produceList.value.toString())
//        total = a.toBigDecimal()
//        quantityLista = ArrayList<Int>(emptyList())
//    }

}
