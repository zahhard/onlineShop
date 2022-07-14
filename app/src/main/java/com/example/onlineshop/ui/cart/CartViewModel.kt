package com.example.onlineshop.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.Status
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.ProduceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor( var commodityRepository: CommodityRepository): ViewModel() {

    val status = MutableLiveData<Status>()
    var orderListLiveData = MutableLiveData<OrderResponse?>()
    var produceItemLiveData = MutableLiveData<ProduceItem?>()
    var orderLiveData = MutableLiveData<OrderResponse?>()


    fun addToCart (order: OrderResponse){
        viewModelScope.launch {
            orderListLiveData.value = commodityRepository.addToCart(order).data
        }
    }

    fun getMyOrder(id : Int){
        viewModelScope.launch {
            status.value = Status.LOADING
            orderListLiveData.value = commodityRepository.getCustomerOrders(id).data
            status.value = Status.DONE
        }
    }

    fun getItemDetail(id: Int) {
        status.value = Status.LOADING
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id).data
            status.value = Status.DONE

        }
    }

    fun updateCart (order : OrderResponse, orderId : Int){
        status.value = Status.LOADING
        viewModelScope.launch {
            commodityRepository.updateCart(order, orderId)
            status.value = Status.DONE
        }
    }

    fun deleteOrder(id: Int){
        status.value = Status.LOADING
        viewModelScope.launch {
            commodityRepository.deleteOrder(id)
            status.value = Status.DONE
        }
    }
//
//    fun addToCart(order: OrderResponse) {
//        viewModelScope.launch {
//            commodityRepository.addToCart(order)
//        }
//    }
}