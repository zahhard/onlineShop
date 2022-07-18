package com.example.onlineshop.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.Customer
import com.example.onlineshop.model.Data
import com.example.onlineshop.model.OrderResponse
import com.example.onlineshop.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoginViewModel @Inject constructor(var commodityRepository: CommodityRepository) : ViewModel() {


    var id = Random.nextInt(10000)
    val status = MutableLiveData<Status>()
    var customer = MutableLiveData<Customer?>()
    var orderLiveData = MutableLiveData<OrderResponse?>()

    fun register(fName: String, lName: String, email: String) {
        viewModelScope.launch {
            status.value = Status.LOADING
            val data = Data(first_name = fName, last_name = lName, email = email)
            customer.value = commodityRepository.register(data).data
            status.value = Status.DONE
        }
    }


    fun addToCart(order: OrderResponse) {
        viewModelScope.launch {
            status.value = Status.LOADING
            orderLiveData.value = commodityRepository.addToCart(order).data
            status.value = Status.DONE
        }
    }
}