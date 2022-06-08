package data.repository

import model.Category
import javax.inject.Inject

class CommodityRepository @Inject constructor (val localDataSource: LocalDataSource,
                                               val remoteDataSource: RemoteDataSource) {

    suspend fun getCategoryList(): List<Category>{
        return remoteDataSource.getCategoryList()
    }
}