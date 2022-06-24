package ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Data
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(var commodityRepository: CommodityRepository) : ViewModel(){

    fun register(fName: String, lName: String, email: String) {
        viewModelScope.launch {
            val data = Data(first_name = fName, last_name = lName, email = email)
            commodityRepository.register(data)
        }
    }
}