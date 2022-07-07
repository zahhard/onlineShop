package data.network

import model.*
import retrofit2.http.*

interface ApiService {

    @GET("products/categories")
    suspend fun getCategoryList(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret
    ) : List<Category>


    @GET("products")
    suspend fun getProduceList(
        @Query("orderby") orderby : String ,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<ProduceItem>


    @GET("products/{id}")
    suspend fun getItemDetail(
        @Path(value = "id") id : Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : ProduceItem


    @GET("products")
    suspend fun getInsideOfCategory(
        @Query(value = "category") id : Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<ProduceItem>


    @GET("products")
    suspend fun search(
        @Query(value = "search") searchParam : String,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<ProduceItem>


    @GET("products")
    suspend fun filter(
        @Query(value = "search") searchParam : String,
        @Query(value = "max_price") maxPrice : String,
        @Query(value = "orderby") orderby : String,
        @Query(value = "attribute") attribute : String,
        @Query(value = "attribute_term") attributeTerm : List<String>,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<ProduceItem>


    @POST("customers")
    suspend fun register(
        @Body data : Data,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : Data


    @POST("orders")
    suspend fun addToCart(
        @Body data : Order,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : Order

    @GET("products/attributes/3/terms")
    suspend fun getColors(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<Color2>

    @GET("products/attributes/4/terms")
    suspend fun getSize(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<Size2>


}