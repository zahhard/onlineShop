package com.example.onlineshop.data.network


import com.example.onlineshop.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("products/categories")
    suspend fun getCategoryList(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret
    ) : List<com.example.onlineshop.model.Category>


    @GET("products")
    suspend fun getProduceList(
        @Query("orderby") orderby : String,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<com.example.onlineshop.model.ProduceItem>


    @GET("products/{id}")
    suspend fun getItemDetail(
        @Path(value = "id") id : Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : com.example.onlineshop.model.ProduceItem


    @GET("products")
    suspend fun getInsideOfCategory(
        @Query(value = "category") id : Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<com.example.onlineshop.model.ProduceItem>


    @GET("products")
    suspend fun getSimilarProducts(
        @Query(value = "category") id : Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<com.example.onlineshop.model.ProduceItem>


    @GET("products")
    suspend fun search(
        @Query(value = "search") searchParam : String,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<com.example.onlineshop.model.ProduceItem>


    @GET("products")
    suspend fun filter(
        @Query(value = "search") searchParam : String,
        @Query(value = "max_price") maxPrice : String,
        @Query(value = "orderby") orderby : String,
        @Query(value = "attribute") attribute : String,
        @Query(value = "attribute_term") attributeTerm : List<String>,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<com.example.onlineshop.model.ProduceItem>


    @POST("customers")
    suspend fun register(
        @Body data : Data,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : Response<Customer>

    @GET("products/attributes/3/terms")
    suspend fun getColors(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<com.example.onlineshop.model.Color2>

    @GET("products/attributes/4/terms")
    suspend fun getSize(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<com.example.onlineshop.model.Size2>

    @POST("orders")
    suspend fun addToCart(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
        @Body data : OrderResponse,
    ) : OrderResponse

    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Path("id") id: Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
        @Body data : OrderResponse, //List<LineItem>,
    ) : Response<OrderResponse>

//    @POST("orders")
//    suspend fun getCustomerOrders(
//        @Query("customer") customerId: Int,
//        @Query("status") status: String = "pending",
//        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
//        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
//    ) : OrderResponse

    @GET("orders/{id}")
    suspend fun getCustomerOrders(
        @Path("id") id: Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,

    ) : OrderResponse

    @GET("products/reviews")
    suspend fun getProductReviews(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
        @Query("product") productId: Int,
        @Query("per_page") perPage: Int = 100
    ): Response<List<CommentsItem>>

}