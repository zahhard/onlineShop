package ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentHomeBinding
import com.example.onlineshop.databinding.FragmentLoginBinding
import com.example.onlineshop.databinding.FragmentSearchResultBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.home.HomeViewModel


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
        binding.login.setOnClickListener {
            loginViewModel.register(binding.firstNme.text.toString(), binding.lastName.text.toString(), binding.email.text.toString())
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putString("name", binding.firstNme.text.toString())
            editor.apply()
        }
    }
}