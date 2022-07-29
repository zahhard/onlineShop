package com.example.onlineshop.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.Status
import kotlinx.coroutines.launch
import com.example.onlineshop.model.Category
import com.example.onlineshop.model.Data
import com.example.onlineshop.model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<Status>()
    var categoryListLiveData = MutableLiveData<List<Category>?>()
    var produceLiveDataPopular = MutableLiveData<List<ProduceItem>?>()
    var produceLiveDataRating = MutableLiveData<List<ProduceItem>?>()
    var produceLiveDataNew = MutableLiveData<List<ProduceItem>?>()
    var specialProduceLiveData= MutableLiveData<ProduceItem?>()
    var statusMessage = ""


    fun getCategoryList(){

        status.value = Status.LOADING

        viewModelScope.launch {
            categoryListLiveData.value = commodityRepository.getCategoryList().data
            if (commodityRepository.getCategoryList().status == Status.DONE)
                status.value = Status.DONE
            else {
                statusMessage = commodityRepository.getCategoryList().serverMessage?.message ?: commodityRepository.getCategoryList().message
                status.value = commodityRepository.getCategoryList().status
            }
        }


    }

    fun getProduceOrderByPopularity(){
        status.value = Status.LOADING
        viewModelScope.launch {
            if (commodityRepository.getProduceOrderByPopularity("popularity").status == Status.DONE)
            produceLiveDataPopular.value = commodityRepository.getProduceOrderByPopularity("popularity").data
            else{
            status.value = commodityRepository.getProduceOrderByPopularity("popularity").status
        }
        }
    }

    fun getProduceOrderByRating(){
        status.value = Status.LOADING
        viewModelScope.launch {
            produceLiveDataRating.value = commodityRepository.getProduceOrderByPopularity("rating").data
            status.value=Status.DONE
        }
    }

    fun getProduceOrderByDate(){
        status.value = Status.LOADING
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.getProduceOrderByPopularity("date").data
        }
    }

    fun getItemDetail() {
        status.value = Status.LOADING
        viewModelScope.launch {
            specialProduceLiveData.value = commodityRepository.getItemDetail(608).data
        }
    }
}