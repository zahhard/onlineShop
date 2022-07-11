package com.example.onlineshop.ui.search_result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.ApiStatus
import kotlinx.coroutines.launch
import com.example.onlineshop.model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<ApiStatus>()
    var produceLiveDataNew = MutableLiveData<List<ProduceItem>>()
    var text = MutableLiveData<String>()

    fun search(text: String){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.search(text)
            status.value = ApiStatus.DONE
        }
    }


    fun filter(id: String, maxPrice: String, orderBy: String , attribute: String, attributeTerm: List<String>){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            if (orderBy == "date" || orderBy == "")
                produceLiveDataNew.value = commodityRepository.filter(id, maxPrice,"date", attribute, attributeTerm)
            else if (orderBy == "cheep")
                produceLiveDataNew.value = commodityRepository.filter(id, maxPrice,"price", attribute, attributeTerm).reversed()
            else if (orderBy == "price")
                produceLiveDataNew.value = commodityRepository.filter(id, maxPrice,"price", attribute, attributeTerm)

            status.value = ApiStatus.DONE
        }
    }
}