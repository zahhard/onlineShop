package ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("login", Context.MODE_PRIVATE)

        goToCart()

        binding.login.setOnClickListener {

            loginViewModel.register(
                binding.firstNme.text.toString(),
                binding.lastName.text.toString(),
                binding.email.text.toString()
            )

            loginViewModel.customer.observe(viewLifecycleOwner) {
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putInt("name", 1 /*it.id*/
             )
                editor.apply()
                goToCart()
            }


        }
    }

    private fun goToCart() {
        if (sharedPreferences.getInt("name", -1) != -1){
            findNavController().navigate(R.id.action_loginFragment_to_cartFragment)
        }
    }
}