package com.example.onlineshop.data.repository

import com.example.onlineshop.data.network.ApiService
import com.example.onlineshop.error_handeling.NetworkCall
import com.example.onlineshop.error_handeling.Resource
import com.example.onlineshop.model.*
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getCategoryList(): Resource<List<Category>> {

        return object : NetworkCall<List<Category>>() {
            override suspend fun createCall(): Response<List<Category>> {
                return apiService.getCategoryList()
            }
        }.fetch()
    }

    suspend fun getProduceOrderByPopularity(orderBy : String): Resource<List<ProduceItem>> {
        return object : NetworkCall<List<ProduceItem>>() {
            override suspend fun createCall(): Response<List<ProduceItem>> {
                return apiService.getProduceList(orderBy)
            }
        }.fetch()
    }

    suspend fun getItemDetail(id: Int): Resource<ProduceItem> {

        return object : NetworkCall<ProduceItem>() {
            override suspend fun createCall(): Response<ProduceItem> {
                return apiService.getItemDetail(id)
            }
        }.fetch()

    }

    suspend fun getInsideOfCategory(id: Int): Resource<List<ProduceItem>> {

        return object : NetworkCall<List<ProduceItem>>() {
            override suspend fun createCall(): Response<List<ProduceItem>> {
                return apiService.getInsideOfCategory(id)
            }
        }.fetch()
    }

    suspend fun search(id: String): Resource<List<ProduceItem>> {

        return object : NetworkCall<List<ProduceItem>>() {
            override suspend fun createCall(): Response<List<ProduceItem>> {
                return apiService.search(id)
            }
        }.fetch()

    }

    suspend fun filter(id: String, maxPrice: String, orderBy: String, attribute: String, attributeTerm: List<String>): Resource<List<ProduceItem>> {

        return object : NetworkCall<List<ProduceItem>>() {
            override suspend fun createCall(): Response<List<ProduceItem>> {
                return apiService.filter( id, maxPrice, orderBy, attribute, attributeTerm )
            }
        }.fetch()


    }

    suspend fun register(user : Data) : Resource<Customer> {

        return object : NetworkCall<Customer>() {
            override suspend fun createCall(): Response<Customer> {
                return apiService.register(user)
            }
        }.fetch()
    }

    suspend fun addToCart(item : OrderResponse) : Resource<OrderResponse> {

        return object : NetworkCall<OrderResponse>() {
            override suspend fun createCall(): Response<OrderResponse> {
                return apiService.addToCart(data = item)
            }
        }.fetch()
    }

    suspend fun getColors(  ) : Resource<List<Color2>>{

        return object : NetworkCall<List<Color2>>() {
            override suspend fun createCall(): Response<List<Color2>> {
                return apiService.getColors()
            }
        }.fetch()
    }

    suspend fun getSize(): Resource<List<Size2>> {

        return object : NetworkCall<List<Size2>>() {
            override suspend fun createCall(): Response<List<Size2>> {
                return apiService.getSize()
            }
        }.fetch()
    }

    suspend fun getCustomerOrders(id: Int): Resource<OrderResponse> {

        return object : NetworkCall<OrderResponse>() {
            override suspend fun createCall(): Response<OrderResponse> {
                return apiService.getCustomerOrders(id = id)            }
        }.fetch()
    }

    suspend fun getProductReviews(id: Int): Resource<List<CommentsItem>> {

        return object : NetworkCall<List<CommentsItem>>() {
            override suspend fun createCall(): Response<List<CommentsItem>> {
                return apiService.getProductReviews(productId = id)
            }
        }.fetch()

    }

    suspend fun updateCart(order: OrderResponse, id: Int): Resource<OrderResponse> {

        return object : NetworkCall<OrderResponse>() {
            override suspend fun createCall(): Response<OrderResponse> {
                return apiService.updateOrder(id = id, data = order)
            }
        }.fetch()

    }

    suspend fun postComment(commentsItem: CommentSent): Resource<CommentsItem> {

        return object : NetworkCall<CommentsItem>() {
            override suspend fun createCall(): Response<CommentsItem> {
                return apiService.postComment(commentsItem)            }
        }.fetch()
    }

    suspend fun deleteOrder(id : Int): Resource<ProduceItem> {

        return object : NetworkCall<ProduceItem>() {
            override suspend fun createCall(): Response<ProduceItem> {
                return apiService.deleteOrder(id = id)            }
        }.fetch()
    }

    suspend fun deleteComment(id: Int) : Resource<CommentsItem>{

        return object : NetworkCall<CommentsItem>() {
            override suspend fun createCall(): Response<CommentsItem> {
                return apiService.deleteComment(id)
            }
        }.fetch()


    }


}