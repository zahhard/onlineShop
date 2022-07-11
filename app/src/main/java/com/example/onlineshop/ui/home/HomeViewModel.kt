package com.example.onlineshop.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.ApiStatus
import kotlinx.coroutines.launch
import com.example.onlineshop.model.Category
import com.example.onlineshop.model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<ApiStatus>()
    var categoryListLiveData = MutableLiveData<List<Category>>()
    var produceLiveDataPopular = MutableLiveData<List<ProduceItem>>()
    var produceLiveDataRating = MutableLiveData<List<ProduceItem>>()
    var produceLiveDataNew = MutableLiveData<List<ProduceItem>>()
    var specialProduceLiveData= MutableLiveData<ProduceItem>()

    fun getCategoryList(){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            categoryListLiveData.value = commodityRepository.getCategoryList()
        }
    }

    fun getProduceOrderByPopularity(){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceLiveDataPopular.value = commodityRepository.getProduceOrderByPopularity("popularity")
        }
    }

    fun getProduceOrderByRating(){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceLiveDataRating.value = commodityRepository.getProduceOrderByPopularity("rating")
            status.value=ApiStatus.DONE
        }
    }

    fun getProduceOrderByDate(){
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.getProduceOrderByPopularity("date")
        }
    }

    fun getItemDetail() {
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            specialProduceLiveData.value = commodityRepository.getItemDetail(608)
        }
    }
}