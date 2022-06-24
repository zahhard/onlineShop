package data.repository

import android.util.Log
import model.Category
import model.Data
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

    suspend fun getInsideOfCategory(id: Int): List<ProduceItem> {
        return remoteDataSource.getInsideOfCategory(id)
    }

    suspend fun search(id: String): List<ProduceItem> {
        return remoteDataSource.search(id)
    }

    suspend fun filter(id: String, maxPrice: String, orderBy: String, onSale: String): List<ProduceItem> {
        return remoteDataSource.filter( id, maxPrice, orderBy, onSale )
    }

    suspend fun register(user : Data) : Data {
        return remoteDataSource.register(user)
    }
}