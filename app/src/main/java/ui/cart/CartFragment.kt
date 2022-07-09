package ui.cart

import adapter.CategoryAdapter
import adapter.OrderAdapter
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
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentCartBinding
import com.example.onlineshop.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import model.LineItemX
import ui.detail.DetailViewModel

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
        var customerId = sharedPreferences.getInt("name", -1)

//        cartViewModel.getMyOrder(customerId)
        cartViewModel.orderListLiveData.observe(viewLifecycleOwner){
            var lista = ArrayList<LineItemX>()
            for (i in 0 until it.size){
                for (j in 0 until it[i].line_items.size){
                    lista.add(it[i].line_items[j])
                }
            }

            if (lista.isEmpty()) {
                Toast.makeText(requireContext(),"empty", Toast.LENGTH_SHORT).show()
            }
            val manager = LinearLayoutManager(requireContext())
            binding.recyclerview.setLayoutManager(manager)
            var adapter = OrderAdapter("","",this) {  }
            adapter.submitList(lista)
            binding.recyclerview.setAdapter(adapter)
            binding.recyclerview.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
    }
}