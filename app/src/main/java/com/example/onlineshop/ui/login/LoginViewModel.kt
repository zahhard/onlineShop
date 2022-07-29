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
    val status = MutableLiveData<Status>(Status.DONE)
    var customer = MutableLiveData<Customer?>()
    var orderLiveData = MutableLiveData<OrderResponse?>()
    var statusMessage = ""

    fun register(fName: String, lName: String, email: String) {
        viewModelScope.launch {

            status.value = Status.LOADING

            val data = Data(first_name = fName, last_name = lName, email = email)
            val register = commodityRepository.register(data)

            customer.value = register.data
            if (register.status == Status.DONE)
                status.value = Status.DONE
            else{
                statusMessage = register.serverMessage?.message ?: register.message
                status.value = register.status
            }
        }
    }


    fun addToCart(order: OrderResponse) {
        viewModelScope.launch {

            var addToCart = commodityRepository.addToCart(order)

            status.value = Status.LOADING
            orderLiveData.value = addToCart.data

            if (addToCart.status == Status.DONE)
                status.value = Status.DONE
            else{
                statusMessage = addToCart.serverMessage?.message ?: addToCart.message
                status.value = addToCart.status
            }
        }
    }
}