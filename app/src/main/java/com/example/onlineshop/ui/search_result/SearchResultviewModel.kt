package com.example.onlineshop.ui.search_result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.Status
import kotlinx.coroutines.launch
import com.example.onlineshop.model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<Status>()
    var produceLiveDataNew = MutableLiveData<List<ProduceItem>?>()
    var text = MutableLiveData<String?>()

    fun search(text: String){
        status.value = Status.LOADING
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.search(text).data
            status.value = Status.DONE
        }
    }

    fun getProduceOrderBy(orderByy :String){
        status.value = Status.LOADING
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.getProduceOrderByPopularity(orderByy).data
            status.value=Status.DONE
        }
    }


    fun filter(id: String, maxPrice: String, orderBy: String , attribute: String, attributeTerm: List<String>){
        status.value = Status.LOADING
        viewModelScope.launch {
            if (orderBy == "date" || orderBy == "")
                produceLiveDataNew.value = commodityRepository.filter(id, maxPrice,"date", attribute, attributeTerm).data
            else if (orderBy == "cheep")
                produceLiveDataNew.value = commodityRepository.filter(id, maxPrice,"price", attribute, attributeTerm).data?.reversed()
            else if (orderBy == "price")
                produceLiveDataNew.value = commodityRepository.filter(id, maxPrice,"price", attribute, attributeTerm).data

            status.value = Status.DONE
        }
    }
}