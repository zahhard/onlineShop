package com.example.onlineshop.di

import android.content.Context
import androidx.room.Room
import com.example.onlineshop.data.database.Database
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.onlineshop.data.network.ApiService
import com.example.onlineshop.data.network.NetworkParams
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
//
//    @Singleton
//    @Provides
//    fun provideRetrofit(moshi: Moshi, client: OkHttpClient): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(NetworkParams.BASE_URL)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .client(client)
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//    }
//
//    @Singleton
//    @Provides
//    fun provideClient(): OkHttpClient {
//        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
//
//        return OkHttpClient.Builder()
//            .addInterceptor(logger)
//            .connectTimeout(50, TimeUnit.SECONDS)
//            .build()
//    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkParams.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        return apiService
    }

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "movie_db"
        )
            .fallbackToDestructiveMigration().build()
    }

}