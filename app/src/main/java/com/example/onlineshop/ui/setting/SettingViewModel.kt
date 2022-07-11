package com.example.onlineshop.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.ApiStatus
import kotlinx.coroutines.launch
import com.example.onlineshop.model.Color2
import com.example.onlineshop.model.ProduceItem
import com.example.onlineshop.model.Size2
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<ApiStatus>()
    var colorListLiveData = MutableLiveData<List<com.example.onlineshop.model.Color2>>()
    var sizeListLiveData = MutableLiveData<List<com.example.onlineshop.model.Size2>>()

    fun getColors(){

        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            colorListLiveData.value = commodityRepository.getColors()
        }
    }

    fun getSize(){
        viewModelScope.launch {
            status.value = ApiStatus.LOADING
            sizeListLiveData.value = commodityRepository.getSize()
            status.value = ApiStatus.DONE
        }
    }

}