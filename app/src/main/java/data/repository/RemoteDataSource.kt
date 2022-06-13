package data.repository

import data.network.ApiService
import model.Category
import model.Comments
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

    suspend fun getProduceComments(id: Int): Comments {
        return apiService.getAProduceComments(id)
    }
}