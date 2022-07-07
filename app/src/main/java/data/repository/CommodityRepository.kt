package data.repository

import android.util.Log
import model.*
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

    suspend fun filter(id: String, maxPrice: String, attribute: String, attributeTerm: List<String>): List<ProduceItem> {
        return remoteDataSource.filter( id, maxPrice, attribute, attributeTerm )
    }

    suspend fun register(user : Data) : Data {
        return remoteDataSource.register(user)
    }

    suspend fun addToCart(item : Order) : Order {
        return remoteDataSource.addToCart(item)
    }

    suspend fun getColors(  ) : List<Color2> {
        return remoteDataSource.getColors()
    }

    suspend fun getSize(  ) : List<Size2> {
        return remoteDataSource.getSize()
    }
}