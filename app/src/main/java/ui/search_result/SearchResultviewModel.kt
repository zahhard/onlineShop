package ui.search_result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var produceLiveDataNew = MutableLiveData<List<ProduceItem>>()
    var text = MutableLiveData<String>()

    fun search(text: String){
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.search(text)
        }
    }


    fun filter(id: String, maxPrice: String, orderBy: String , attribute: String, attributeTerm: List<String>){
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.filter(id, maxPrice, attribute, attributeTerm)
        }
    }
}