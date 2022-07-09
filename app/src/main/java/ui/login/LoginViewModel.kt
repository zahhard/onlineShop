package ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import data.repository.CommodityRepository
import kotlinx.coroutines.launch
import model.Customer
import model.Data
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class LoginViewModel @Inject constructor(var commodityRepository: CommodityRepository) : ViewModel(){
    var id = Random.nextInt(10000)

    var customer = MutableLiveData<Data>()

    fun register(fName: String, lName: String, email: String) {
        viewModelScope.launch {
            val data = Data(first_name = fName, last_name = lName, email = email)
            commodityRepository.register(data)
        }
    }
}