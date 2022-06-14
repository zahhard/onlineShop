package ui.each_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class EachCategoryViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var produceItemLiveData = MutableLiveData<List<ProduceItem>>()

    fun getInsideOfCategory(id: Int) {
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getInsideOfCategory(id)
        }
    }
}