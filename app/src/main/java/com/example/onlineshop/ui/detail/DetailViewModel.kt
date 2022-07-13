package com.example.onlineshop.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<ApiStatus>()
    var produceItemLiveData = MutableLiveData<ProduceItem>()
    var commentLiveData = MutableLiveData<CommentsItem>()
    var produceCommentsLiveData = MutableLiveData<List<CommentsItem>>()
    var orderLiveData = MutableLiveData<OrderResponse>()
    var produceIdList = ArrayList<Int>()

    fun getItemDetail(id: Int) {
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id)
        }
    }

    fun getProduceComments(id: Int) {
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceCommentsLiveData.value = commodityRepository.getProductReviews(id)
            status.value = ApiStatus.DONE
        }
    }


    fun updateCart (order : OrderResponse, orderId : Int){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            orderLiveData.value = commodityRepository.updateCart(order, orderId).body()
            status.value = ApiStatus.DONE
        }
    }

    fun postComment(commentsItem: CommentSent) {
        viewModelScope.launch {
           commentLiveData.value = commodityRepository.postComment(commentsItem).body()
        }
    }
}