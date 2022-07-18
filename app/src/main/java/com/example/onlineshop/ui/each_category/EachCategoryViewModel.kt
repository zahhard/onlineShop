package com.example.onlineshop.ui.each_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.onlineshop.data.repository.CommodityRepository
import com.example.onlineshop.model.ProduceItem
import com.example.onlineshop.model.Status
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EachCategoryViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    val status = MutableLiveData<Status>()
    var produceItemLiveData = MutableLiveData<List<ProduceItem>?>()

    fun getInsideOfCategory(id: Int) {
        status.value = Status.LOADING
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getInsideOfCategory(id).data
            status.value=Status.DONE
        }
    }
}