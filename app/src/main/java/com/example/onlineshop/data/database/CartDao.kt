package com.example.onlineshop.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.onlineshop.model.CartProduct

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartProduct: CartProduct)

    @Query("SELECT * FROM CartProduct WHERE id = :id")
    suspend fun getCartProduct(id: Int):CartProduct

    @Query("SELECT EXISTS(SELECT * FROM CartProduct WHERE id = :id)")
    suspend fun exists(id : Int) : Boolean

    @Query("SELECT * FROM CartProduct ")
     fun getAllProducts(): LiveData<List<CartProduct>>

    @Query("DELETE FROM CartProduct ")
    suspend fun emptyCart()

    @Query("DELETE FROM CartProduct WHERE id = :id")
    suspend fun deleteProduct(id: Int)

    @Query("SELECT SUM(totalPrice) FROM CartProduct")
    fun getTotalPrice(): LiveData<String>
}