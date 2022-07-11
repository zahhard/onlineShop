package com.example.onlineshop.data.repository

import com.example.onlineshop.model.*
import retrofit2.Response
import javax.inject.Inject

class CommodityRepository @Inject constructor (val localDataSource: LocalDataSource,
                                               val remoteDataSource: RemoteDataSource
) {

    suspend fun getCategoryList(): List<com.example.onlineshop.model.Category>{
        return remoteDataSource.getCategoryList()
    }

    suspend fun getProduceOrderByPopularity(orderBy : String): List<com.example.onlineshop.model.ProduceItem> {
        return remoteDataSource.getProduceOrderByPopularity(orderBy)
    }

    suspend fun getItemDetail(id: Int) : com.example.onlineshop.model.ProduceItem {
        return remoteDataSource.getItemDetail(id)
    }

    suspend fun getInsideOfCategory(id: Int): List<com.example.onlineshop.model.ProduceItem> {
        return remoteDataSource.getInsideOfCategory(id)
    }

    suspend fun search(id: String): List<com.example.onlineshop.model.ProduceItem> {
        return remoteDataSource.search(id)
    }

    suspend fun filter(id: String, maxPrice: String, orderBy : String, attribute: String, attributeTerm: List<String>): List<com.example.onlineshop.model.ProduceItem> {
        return remoteDataSource.filter( id, maxPrice, orderBy, attribute, attributeTerm )
    }

    suspend fun register(user : Data) : Response<Customer> {
        return remoteDataSource.register(user)
    }

    suspend fun addToCart(item : OrderResponse) : OrderResponse {
        return remoteDataSource.addToCart(item)
    }

    suspend fun getColors(  ) : List<com.example.onlineshop.model.Color2> {
        return remoteDataSource.getColors()
    }

    suspend fun getSize(  ) : List<com.example.onlineshop.model.Size2> {
        return remoteDataSource.getSize()
    }

    suspend fun getCustomerOrders(id: Int): OrderResponse {
        return remoteDataSource.getCustomerOrders(id)
    }

    suspend fun getProductReviews(id: Int): List<CommentsItem> {
        return remoteDataSource.getProductReviews(id).body()!!
    }

    suspend fun updateCart(order: OrderResponse , id: Int): Response<OrderResponse> {
        return  remoteDataSource.updateCart(order, id)
    }

    suspend fun postComment(commentsItem: CommentSent): Response<CommentsItem> {
        return  remoteDataSource.postComment(commentsItem)
    }
}