package ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Category
import model.ProduceItem
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var categoryListLiveData = MutableLiveData<List<Category>>()
    var produceLiveDataPopular = MutableLiveData<List<ProduceItem>>()
    var produceLiveDataRating = MutableLiveData<List<ProduceItem>>()
    var produceLiveDataNew = MutableLiveData<List<ProduceItem>>()

    fun getCategoryList(){
        viewModelScope.launch {
            categoryListLiveData.value = commodityRepository.getCategoryList()
        }
    }

    fun getProduceOrderByPopularity(){
        viewModelScope.launch {
            produceLiveDataPopular.value = commodityRepository.getProduceOrderByPopularity("popularity")
        }
    }

    fun getProduceOrderByRating(){
        viewModelScope.launch {
            produceLiveDataRating.value = commodityRepository.getProduceOrderByPopularity("rating")
        }
    }

    fun getProduceOrderByDate(){
        viewModelScope.launch {
            produceLiveDataNew.value = commodityRepository.getProduceOrderByPopularity("date")
        }
    }
}