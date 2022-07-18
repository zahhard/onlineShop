package com.example.onlineshop.data.database

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import retrofit2.http.Query
//
//@Dao
//interface Dao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insert(orderId: OrderId)
//
//    @Query("SELECT * FROM OrderId LIMIT 1")
//    suspend fun getOrder():OrderId?
//
//    @Query("DELETE FROM OrderId")
//    suspend fun delete()
//
//}