package ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Order
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor( var commodityRepository: CommodityRepository): ViewModel() {

    var orderListLiveData = MutableLiveData<List<Order>>()

    fun addToCart (order: Order){
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