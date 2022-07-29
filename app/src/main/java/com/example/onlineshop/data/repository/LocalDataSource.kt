package com.example.onlineshop.data.repository

import androidx.lifecycle.LiveData
import com.example.onlineshop.data.database.Database
import com.example.onlineshop.model.CartProduct
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val database: Database) {

    //cartProduct
    suspend fun insertCartProduct(cartProduct: CartProduct) = database.cartDao.insert(cartProduct)

    suspend fun emptyCart() = database.cartDao.emptyCart()

    suspend fun exists(id: Int) = database.cartDao.exists(id)

    suspend fun deleteProduct(id: Int) = database.cartDao.deleteProduct(id)

    fun getAllCartProducts() = database.cartDao.getAllProducts()

    suspend fun getCartProduct(id: Int) = database.cartDao.getCartProduct(id)

    fun getTotalPrice() = database.cartDao.getTotalPrice()
}