package com.example.onlineshop.data.repository

import com.example.onlineshop.data.network.ApiService
import com.example.onlineshop.model.*
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getCategoryList(): List<com.example.onlineshop.model.Category>{
        return apiService.getCategoryList()
    }

    suspend fun getProduceOrderByPopularity(orderBy : String): List<com.example.onlineshop.model.ProduceItem>{
        return apiService.getProduceList(orderBy)
    }

    suspend fun getItemDetail(id: Int): com.example.onlineshop.model.ProduceItem {
        return apiService.getItemDetail(id)
    }

    suspend fun getInsideOfCategory(id: Int): List<com.example.onlineshop.model.ProduceItem> {
        return apiService.getInsideOfCategory(id)
    }

    suspend fun search(id: String): List<com.example.onlineshop.model.ProduceItem> {
        return apiService.search(id)
    }

    suspend fun filter(id: String, maxPrice: String, orderBy: String, attribute: String, attributeTerm: List<String>): List<com.example.onlineshop.model.ProduceItem> {
        return apiService.filter( id, maxPrice, orderBy, attribute, attributeTerm )
    }

    suspend fun register(user : Data) : Response<Customer> {
        return apiService.register(user)
    }

    suspend fun addToCart(item : OrderResponse) : OrderResponse {
        return apiService.addToCart(data = item)
    }

    suspend fun getColors(  ) : List<com.example.onlineshop.model.Color2> {
        return apiService.getColors()
    }

    suspend fun getSize(): List<com.example.onlineshop.model.Size2> {
        return apiService.getSize()
    }

    suspend fun getCustomerOrders(id: Int): OrderResponse {
        return apiService.getCustomerOrders(id = id)
    }

    suspend fun getProductReviews(id: Int): Response<List<CommentsItem>> {
        return apiService.getProductReviews(productId = id)
    }

    suspend fun updateCart(order: OrderResponse, id: Int): Response<OrderResponse> {
        return apiService.updateOrder(id = id, data = order)
    }

    suspend fun postComment(commentsItem: CommentSent): Response<CommentsItem> {
        return apiService.postComment(commentsItem)
    }

    suspend fun deleteOrder(id : Int): Response<ProduceItem> {
        return apiService.deleteOrder(id = id)
    }

    suspend fun deleteComment(id: Int) : Response<CommentsItem>{
       return apiService.deleteComment(id)
    }


}