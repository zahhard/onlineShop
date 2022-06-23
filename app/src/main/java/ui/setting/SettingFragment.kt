package ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {


    private lateinit var binding: FragmentSettingBinding
    private val settingViewModel : SettingViewModel by viewModels()
    lateinit var ppreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ppreferences = requireActivity().getSharedPreferences("search", Context.MODE_PRIVATE)


        binding.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.seekbarText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        binding.button.setOnClickListener {

            var orderBy = ""
            if (binding.cheep.isChecked)
                orderBy = "cheep"
            if (binding.expensive.isChecked)
                orderBy = "expensive"
            if (binding.today.isChecked)
                orderBy = "date"


            val editor: SharedPreferences.Editor = ppreferences.edit()
            editor.putBoolean("on_sale", binding.onOrder.isChecked)
            editor.putString("orderBy", orderBy)
            editor.putString("max_price", binding.seekbarText.text.toString())
            editor.apply()

            findNavController().navigate(R.id.action_settingFragment_to_searchResultFragment)

        }

    }
}