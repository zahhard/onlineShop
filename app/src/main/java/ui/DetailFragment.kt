package ui

import adapter.CategoryAdapter
import adapter.SliderAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Carousel
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.onlineshop.R
import com.example.onlineshop.databinding.FragmentDetailBinding
import com.example.onlineshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    val detailViewModel : DetailViewModel by viewModels()
    var imageCont : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var itemId = requireArguments().getInt("filmId", -1)
        detailViewModel.getItemDetail(itemId)

        var urlList = ArrayList<String>()

        detailViewModel.produceItemLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvDetailName.text = it.name
                binding.tvDetailReview.text = it.price + " تومان "
                imageCont = it.images.size

                for (image in it.images){
                    urlList.add(
                        image.src
                    )
                }

                binding.viewPagerImageSlider.adapter = SliderAdapter(this, urlList, binding.viewPagerImageSlider )
            }
        }



    }}