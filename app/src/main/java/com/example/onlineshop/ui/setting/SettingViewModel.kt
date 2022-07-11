package com.example.onlineshop.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import kotlinx.coroutines.launch
import com.example.onlineshop.model.Color2
import com.example.onlineshop.model.ProduceItem
import com.example.onlineshop.model.Size2
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var colorListLiveData = MutableLiveData<List<com.example.onlineshop.model.Color2>>()
    var sizeListLiveData = MutableLiveData<List<com.example.onlineshop.model.Size2>>()

    fun getColors(){
        viewModelScope.launch {
            colorListLiveData.value = commodityRepository.getColors()
        }
    }

    fun getSize(){
        viewModelScope.launch {
            sizeListLiveData.value = commodityRepository.getSize()
        }
    }

}