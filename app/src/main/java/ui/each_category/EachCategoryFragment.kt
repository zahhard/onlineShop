package ui.each_category

import adapter.CategoryAdapter
import adapter.EachItemAdapter
import adapter.InsideCategoryAdapter
import adapter.SliderAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlineshop.databinding.FragmentEachCategoryBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EachCategoryFragment : Fragment() {

    private lateinit var binding: FragmentEachCategoryBinding
    val eachCategoryViewModel: EachCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEachCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var itemId = requireArguments().getInt("categoryId", -1)
        eachCategoryViewModel.getInsideOfCategory(itemId)


        eachCategoryViewModel.produceItemLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                val manager = LinearLayoutManager(requireContext())
                binding.recyclerviewFff.setLayoutManager(manager)
                var adapter = InsideCategoryAdapter(this) {   }
                adapter.submitList(it)
                binding.recyclerviewFff.setAdapter(adapter)
            }
        }
    }
}