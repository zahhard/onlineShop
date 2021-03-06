package data.repository

import data.network.ApiService
import model.Category
import model.Produce
import model.ProduceItem
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

    suspend fun filter(id: String, maxPrice: String, orderBy: String, onSale: String): List<ProduceItem> {
        return apiService.filter( id, maxPrice, orderBy, onSale )
    }
}