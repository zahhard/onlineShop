package data.network

import model.Category
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products/categories")
    suspend fun getCategoryList(
        @Query("consumer_key") consumerKey : String = NetworkParams.consumer_key,
        @Query("consumer_secret") consumerSecret : String = NetworkParams.consumer_secret
    ) : List<Category>
}