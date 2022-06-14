package ui.detail

import adapter.CategoryAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Comments
import model.CommentsItem
import model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var produceItemLiveData = MutableLiveData<ProduceItem>()
    var produceCommentsLiveData = MutableLiveData<Comments>()

    fun getItemDetail(id: Int) {
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id)
        }
    }

//    fun getProduceComments(id: Int) {
//        viewModelScope.launch {
//            produceCommentsLiveData.value = commodityRepository.getProduceComments(id)
//        }
//    }


    fun detCommentId(comments: ArrayList<CommentsItem>,  commentId: Int): Int{
        var productId : Int = 0
        for (comment in comments){
            if (commentId == comment.product_id)
                productId = comment.product_id
        }
        return productId
    }
}