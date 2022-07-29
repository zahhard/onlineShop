package com.example.onlineshop.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.CartProduct
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.ProduceItem
import com.example.onlineshop.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(var commodityRepository: CommodityRepository) :
    ViewModel() {

    val status = MutableLiveData<Status>()
    var orderListLiveData = MutableLiveData<OrderResponse?>()
    var produceItemLiveData = MutableLiveData<ProduceItem?>()
    var orderLiveData = MutableLiveData<OrderResponse?>()
    var cartProductsLiveData = MutableLiveData<List<CartProduct>>()


    fun addToCart(order: OrderResponse) {
        viewModelScope.launch {
            orderListLiveData.value = commodityRepository.addToCart(order).data
//            orderListLiveData.value = commodityRepository.updateCart()
        }
    }


    fun getAllCartProducts() = commodityRepository.getAllCartProducts()

    fun deleteProduct(id :Int) {
        viewModelScope.async {
            commodityRepository.deleteProduct( id )
        }
    }

    fun getMyOrder(id: Int) {
        viewModelScope.launch {
            orderListLiveData.value = commodityRepository.getCustomerOrders(id).data
        }
    }

    fun getItemDetail(id: Int) {
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id).data
        }
    }

    fun updateCart(order: OrderResponse, orderId: Int) {
        viewModelScope.launch {
            commodityRepository.updateCart(order, orderId)
//
        }
    }

//    fun deleteOrder(id: Int) {
//        status.value = Status.LOADING
//        viewModelScope.launch {
//            commodityRepository.deleteOrder(id)
//            status.value = Status.DONE
//        }
//    }
//
//    fun addToCart(order: OrderResponse) {
//        viewModelScope.launch {
//            commodityRepository.addToCart(order)
//        }
//    }
}