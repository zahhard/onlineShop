package ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Color2
import model.ProduceItem
import model.Size2
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

    var colorListLiveData = MutableLiveData<List<Color2>>()
    var sizeListLiveData = MutableLiveData<List<Size2>>()

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