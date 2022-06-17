package ui.setting

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.ProduceItem
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(val commodityRepository: CommodityRepository) : ViewModel() {

}