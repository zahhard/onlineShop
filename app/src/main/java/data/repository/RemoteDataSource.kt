package data.repository

import android.media.AudioAttributes
import android.util.Log
import com.bumptech.glide.load.engine.Resource
import data.network.ApiService
import model.*
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getCategoryList(): List<Category>{
        return apiService.getCategoryList()
    }

    suspend fun getProduceOrderByPopularity(orderBy : String): List<ProduceItem>{
        return apiService.getProduceList(orderBy)
    }

    suspend fun getItemDetail(id: Int): ProduceItem {
        return apiService.getItemDetail(id)
    }

    suspend fun getInsideOfCategory(id: Int): List<ProduceItem> {
        return apiService.getInsideOfCategory(id)
    }

    suspend fun search(id: String): List<ProduceItem> {
        return apiService.search(id)
    }

    suspend fun filter(id: String, maxPrice: String, orderBy: String, attribute: String, attributeTerm: List<String>): List<ProduceItem> {
        return apiService.filter( id, maxPrice, orderBy, attribute, attributeTerm )
    }

    suspend fun register(user : Data) : Data {
        return apiService.register(user)
    }

    suspend fun addToCart(item : Order) : Order {
        return apiService.addToCart(item)
    }

    suspend fun getColors(  ) : List<Color2> {
        return apiService.getColors()
    }

    suspend fun getSize(): List<Size2> {
        return apiService.getSize()
    }

    suspend fun getCustomerOrders(id: Int): List<Order> {
        return apiService.getCustomerOrders(id)
    }
}