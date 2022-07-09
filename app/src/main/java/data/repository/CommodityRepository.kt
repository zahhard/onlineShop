package data.repository

import android.util.Log
import com.bumptech.glide.load.engine.Resource
import model.*
import retrofit2.Response
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

    suspend fun filter(id: String, maxPrice: String, orderBy : String, attribute: String, attributeTerm: List<String>): List<ProduceItem> {
        return remoteDataSource.filter( id, maxPrice, orderBy, attribute, attributeTerm )
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

    suspend fun getCustomerOrders(id: Int): List<Order> {
        return remoteDataSource.getCustomerOrders(id)
    }
}