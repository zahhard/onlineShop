package ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Category
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var categoryListLiveData = MutableLiveData<List<Category>>()

    fun getCategoryList(){
        viewModelScope.launch {
            categoryListLiveData.value = commodityRepository.getCategoryList()
        }
    }
}