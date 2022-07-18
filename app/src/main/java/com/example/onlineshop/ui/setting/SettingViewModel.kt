package com.example.onlineshop.ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.Color2
import com.example.onlineshop.model.Size2
import com.example.onlineshop.model.Status
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<Status>()
    var colorListLiveData = MutableLiveData<List<Color2>?>()
    var sizeListLiveData = MutableLiveData<List<Size2>?>()

    fun getColors(){

        viewModelScope.launch {
            status.value = Status.LOADING
            colorListLiveData.value = commodityRepository.getColors().data
        }
    }

    fun getSize(){
        viewModelScope.launch {
            status.value = Status.LOADING
            sizeListLiveData.value = commodityRepository.getSize().data
            status.value = Status.DONE
        }
    }

}