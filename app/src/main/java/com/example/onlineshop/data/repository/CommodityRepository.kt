package com.example.onlineshop.data.repository

import com.example.onlineshop.error_handeling.Resource
import com.example.onlineshop.model.*
import retrofit2.Response
import javax.inject.Inject

class CommodityRepository @Inject constructor (val localDataSource: LocalDataSource,
                                               val remoteDataSource: RemoteDataSource
) {

    suspend fun getCategoryList(): Resource<List<Category>>{
        return remoteDataSource.getCategoryList()
    }

    suspend fun getProduceOrderByPopularity(orderBy : String): Resource<List<ProduceItem>> {
        return remoteDataSource.getProduceOrderByPopularity(orderBy)
    }

    suspend fun getItemDetail(id: Int) : Resource<ProduceItem> {
        return remoteDataSource.getItemDetail(id)
    }

    suspend fun getInsideOfCategory(id: Int): Resource<List<ProduceItem>> {
        return remoteDataSource.getInsideOfCategory(id)
    }

    suspend fun search(id: String): Resource<List<ProduceItem>> {
        return remoteDataSource.search(id)
    }

    suspend fun filter(id: String, maxPrice: String, orderBy : String, attribute: String, attributeTerm: List<String>): Resource<List<ProduceItem>> {
        return remoteDataSource.filter( id, maxPrice, orderBy, attribute, attributeTerm )
    }

    suspend fun register(user : Data) : Resource<Customer> {
        return remoteDataSource.register(user)
    }

    suspend fun addToCart(item : OrderResponse) : Resource<OrderResponse> {
        return remoteDataSource.addToCart(item)
    }

    suspend fun getColors(  ) : Resource<List<Color2>> {
        return remoteDataSource.getColors()
    }

    suspend fun getSize(  ) : Resource<List<Size2>> {
        return remoteDataSource.getSize()
    }

    suspend fun getCustomerOrders(id: Int): Resource<OrderResponse> {
        return remoteDataSource.getCustomerOrders(id)
    }

    suspend fun getProductReviews(id: Int): Resource<List<CommentsItem>> {
        return remoteDataSource.getProductReviews(id)
    }

    suspend fun updateCart(order: OrderResponse , id: Int): Resource<OrderResponse> {
        return  remoteDataSource.updateCart(order, id)
    }

    suspend fun postComment(commentsItem: CommentSent): Resource<CommentsItem> {
        return  remoteDataSource.postComment(commentsItem)
    }

    suspend fun deleteOrder(id : Int): Resource<ProduceItem> {
        return remoteDataSource.deleteOrder(id = id)
    }

    suspend fun deleteComment(id: Int): Resource<CommentsItem> {
        return remoteDataSource.deleteComment(id)
    }
}