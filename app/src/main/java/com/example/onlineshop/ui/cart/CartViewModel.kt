package com.example.onlineshop.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.LineItem
import kotlinx.coroutines.launch
import com.example.onlineshop.model.Order
import com.example.onlineshop.model.OrderResponse
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor( var commodityRepository: CommodityRepository): ViewModel() {

    var orderListLiveData = MutableLiveData<OrderResponse>()

    fun addToCart (order: OrderResponse){
        viewModelScope.launch {
            commodityRepository.addToCart(order)
        }
    }

    fun getMyOrder(id : Int){
        viewModelScope.launch {
            orderListLiveData.value = commodityRepository.getCustomerOrders(id)
        }
    }
}