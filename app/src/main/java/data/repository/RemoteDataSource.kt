package data.repository

import data.network.ApiService
import model.Category
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getCategoryList(): List<Category>{
        return apiService.getCategoryList()
    }
}