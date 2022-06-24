package data.network

import model.Category
import model.Data
import model.Produce
import model.ProduceItem
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
        @Query(value = "on_sale") onSale : String,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : List<ProduceItem>


//    @GET("customers")
//    suspend fun createCustomer(
//        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
//        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
//    ) : List<ProduceItem>

    @POST("customers")
    suspend fun register(
        @Body data : Data,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : Data

}