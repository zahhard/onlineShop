package data.repository

import model.Category
import model.Produce
import model.ProduceItem
import javax.inject.Inject

class CommodityRepository @Inject constructor (val localDataSource: LocalDataSource,
                                               val remoteDataSource: RemoteDataSource) {

    suspend fun getCategoryList(): List<Category>{
        return remoteDataSource.getCategoryList()
    }

    suspend fun getProduceOrderByPopularity(orderBy : String): List<ProduceItem> {
        return remoteDataSource.getProduceOrderByPopularity(orderBy)
    }

    suspend fun getItemDetail(id: Int) : ProduceItem{
        return remoteDataSource.getItemDetail(id)
    }
}