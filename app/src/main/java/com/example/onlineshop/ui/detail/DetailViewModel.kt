package com.example.onlineshop.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<Status>()
    var produceItemLiveData = MutableLiveData<ProduceItem?>()
    var commentLiveData = MutableLiveData<CommentsItem?>()
    var produceCommentsLiveData = MutableLiveData<List<CommentsItem>?>()
    var orderLiveData = MutableLiveData<OrderResponse?>()
    var count = MutableLiveData<Int>()

    fun getItemDetail(id: Int) {
        status.value = Status.LOADING
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id).data
        }
    }

    fun getProduceComments(id: Int) {
        status.value = Status.LOADING
        viewModelScope.launch {
            produceCommentsLiveData.value = commodityRepository.getProductReviews(id).data
            status.value = Status.DONE
        }
    }


    fun updateCart (order : OrderResponse, orderId : Int){
        status.value = Status.LOADING
        viewModelScope.launch {
            orderLiveData.value = commodityRepository.updateCart(order, orderId).data
            status.value = Status.DONE
        }
    }

    fun postComment(commentsItem: CommentSent) {
        viewModelScope.launch {
           commentLiveData.value = commodityRepository.postComment(commentsItem).data
        }
    }

    fun deleteComment(id: Int) {
        viewModelScope.async {
           commentLiveData.value = commodityRepository.deleteComment(id).data
        }
    }


    fun insertProductToCart(cartProduct: CartProduct){
        viewModelScope.launch {
            commodityRepository.insertCartProduct(cartProduct)
        }
    }

    fun editComment(id: Int, commentsItem: CommentEdit){
        viewModelScope.launch {
            commodityRepository.putComment(id , commentsItem).data
        }
    }
}