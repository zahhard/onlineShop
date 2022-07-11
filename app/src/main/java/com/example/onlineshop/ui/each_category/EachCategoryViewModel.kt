package com.example.onlineshop.ui.each_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.ApiStatus
import kotlinx.coroutines.launch
import com.example.onlineshop.model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class EachCategoryViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<ApiStatus>()
    var produceItemLiveData = MutableLiveData<List<com.example.onlineshop.model.ProduceItem>>()

    fun getInsideOfCategory(id: Int) {
        status.value = ApiStatus.LOADING
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getInsideOfCategory(id)
            status.value=ApiStatus.DONE
        }
    }
}