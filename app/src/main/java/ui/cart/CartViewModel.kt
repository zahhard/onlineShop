package ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Order
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor( var commodityRepository: CommodityRepository): ViewModel() {

    fun addToCart (order: Order){
        viewModelScope.launch {
            commodityRepository.addToCart(order)
        }
    }
}