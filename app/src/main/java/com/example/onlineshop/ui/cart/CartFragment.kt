package com.example.onlineshop.ui.cart

import com.example.onlineshop.adapter.OrderAdapter
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint
import com.example.onlineshop.model.LineItem

@AndroidEntryPoint
class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    val cartViewModel: CartViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

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
        var customerId = sharedPreferences.getInt("id", -1)
        var name = sharedPreferences.getString("name", "")
        var orderId = sharedPreferences.getInt("orderId", -1)

        binding.name.text = name
        cartViewModel.getMyOrder(orderId)
        cartViewModel.orderListLiveData.observe(viewLifecycleOwner){

            if (it.line_items.size == 0) {
                Toast.makeText(requireContext(),"empty", Toast.LENGTH_SHORT).show()
            }
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.layoutManager = manager
            var adapter = OrderAdapter("","",this) {  }
            adapter.submitList(it.line_items)
            binding.recyclerview.setAdapter(adapter)
        }
    }
}