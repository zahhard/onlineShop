package com.example.onlineshop.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import kotlinx.coroutines.launch
import com.example.onlineshop.model.CommentsItem
import com.example.onlineshop.model.LineItem
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var produceItemLiveData = MutableLiveData<ProduceItem>()
    var produceCommentsLiveData = MutableLiveData<List<CommentsItem>>()
    var orderLiveData = MutableLiveData<OrderResponse>()

    fun getItemDetail(id: Int) {
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id)
        }
    }

    fun getProduceComments(id: Int) {
        viewModelScope.launch {
            produceCommentsLiveData.value = commodityRepository.getProductReviews(id)
        }
    }


    fun updateCart (order : OrderResponse, orderId : Int){
        viewModelScope.launch {
            orderLiveData.value = commodityRepository.updateCart(order, orderId).body()
        }
    }
}