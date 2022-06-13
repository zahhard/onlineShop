package data.network

import model.Category
import model.Comments
import model.Produce
import model.ProduceItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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


    @GET("products/reviews/{product_id}")
    suspend fun getAProduceComments(
        @Path(value = "product_id") id : Int,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : Comments


    @GET("products")
    suspend fun getProduceListBySearch(
        @Query(value = "search") search : String,
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret,
    ) : Comments
}