package ui.detail

import adapter.SliderAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.onlineshop.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import model.CommentsItem
import model.ProduceItem

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private lateinit var binding: FragmentDetailBinding
    val detailViewModel: DetailViewModel by viewModels()
    var imageCont: Int = 0
    var category: String = ""
    var comments = ArrayList<CommentsItem>()
    var urlList = ArrayList<String>()

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
        observeProduceItem()
    }

    private fun observeProduceItem() {
        detailViewModel.produceItemLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvDetailName.text = it.name
                binding.tvDetailReview.text =
                    HtmlCompat.fromHtml(it.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
                imageCont = it.images.size
                getImageUrl(it)
                binding.viewPagerImageSlider.adapter =
                    SliderAdapter(this, urlList, binding.viewPagerImageSlider)
                binding.rating.text = it.average_rating.toString()
                getCategoryList(it)
                binding.tvCategoryList.text = category
            }
        }
    }

    private fun getImageUrl(produceItem: ProduceItem) {
        for (image in produceItem.images) {
            urlList.add(
                image.src
            )
        }
    }

    private fun getCategoryList(produceItem: ProduceItem) {
        for (itemCategory in produceItem.categories) {
            category += itemCategory.name
            category += " / "
        }
    }
}