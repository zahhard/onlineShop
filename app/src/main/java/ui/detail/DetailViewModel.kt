package ui.detail

import adapter.CategoryAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var produceItemLiveData = MutableLiveData<ProduceItem>()

    fun getItemDetail(id: Int) {
        viewModelScope.launch {
            produceItemLiveData.value = commodityRepository.getItemDetail(id)
        }
    }
}