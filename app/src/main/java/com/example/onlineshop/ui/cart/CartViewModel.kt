package com.example.onlineshop.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.ApiStatus
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.ProduceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor( var commodityRepository: CommodityRepository): ViewModel() {

    val status = MutableLiveData<ApiStatus>()
    var orderListLiveData = MutableLiveData<OrderResponse>()
    var produceItemLiveData = MutableLiveData<ProduceItem>()
    var orderLiveData = MutableLiveData<OrderResponse>()


    fun addToCart (order: OrderResponse){
        viewModelScope.launch {
            orderListLiveData.value = commodityRepository.addToCart(order)
        }
    }

    fun getMyOrder(id : Int){
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            orderListLiveData.value = commodityRepository.getCustomerOrders(id)
            status.value = ApiStatus.DONE
        }
    }

    fun getItemDetail(id: Int) {
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id)
            status.value = ApiStatus.DONE

        }
    }

    fun updateCart (order : OrderResponse, orderId : Int){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            commodityRepository.updateCart(order, orderId).body()
            status.value = ApiStatus.DONE
        }
    }

    fun deleteOrder(id: Int){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            commodityRepository.deleteOrder(id)
            status.value = ApiStatus.DONE
        }
    }
//
//    fun addToCart(order: OrderResponse) {
//        viewModelScope.launch {
//            commodityRepository.addToCart(order)
//        }
//    }
}