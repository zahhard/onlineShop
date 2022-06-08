package data.repository

import data.network.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) {
}