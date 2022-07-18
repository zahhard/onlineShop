package com.example.onlineshop.ui.cart

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.example.onlineshop.model.LineItem
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.ProduceItem
import com.example.onlineshop.model.Status
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.math.BigDecimal


@AndroidEntryPoint
class CartFragment : Fragment(), LocationListener {
    val locationPermissionCode = 1001

    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private var produceList = MutableLiveData<List<ProduceItem?>>(emptyList())
    private var finalIdList = ArrayList<Int>(emptyList())
    private var quantityLista = ArrayList<Int>(emptyList())
    private var orderId = -1
    private var locationManager: LocationManager? = null

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

        ///**********************************    (  call fun  )   **********************************

//        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager?

        setListOfProductId()
        observeStatus()
        setUserName()
        callOrderRequest()
        finalPayment()
        getEachProduct()
        setRecyclerView()
        setListOfQuantitty()


        binding.themeMode.setOnClickListener {
            requireContext().theme.changingConfigurations

            chooseThemeDialog()
        }

        binding.map.setOnClickListener {


            locationManager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if ((ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED)
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionCode
                )
            }
            requireActivity().let {
                locationManager!!.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    5f,
                    this
                )
            }

            findNavController().navigate(R.id.action_cartFragment_to_mapsFragment)
        }


        ///*****************************************************************************************

        binding.ddprice.text = total.toString()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
            }
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
                    Log.d("eeeeeeeee", sharedPreferences.getString("theme", "")!!)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    delegate.applyDayNight()

                }
                1 -> {
                    editor.putString("theme", "night")
                    editor.apply()
                    Log.d("eeeeeeeee", sharedPreferences.getString("theme", "")!!)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    delegate.applyDayNight()

                    dialog.dismiss()

                }
                2 -> {
                    dialog.dismiss()
                    editor.putString("theme", "system")
                    editor.apply()
                    Log.d("eeeeeeeee", sharedPreferences.getString("theme", "")!!)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
//                    delegate.applyDayNight()

                }

            }
        }

        val dialog = builder.create()
        dialog.show()
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
                    binding.ddprice.text = total.toString()
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

    override fun onLocationChanged(p0: Location) {

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("Latitude", p0.latitude.toString())
        editor.putString("Longitude", p0.longitude.toString())
        editor.apply()

        Toast.makeText(
            requireContext(),
            "Latitude: " + p0.latitude + " , Longitude: " + p0.longitude,
            Toast.LENGTH_SHORT
        ).show()
    }

}
