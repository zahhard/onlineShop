package com.example.onlineshop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartProduct(
    @PrimaryKey
    var id:Int,
    var name:String,
    var image: String,
    var quantity:Int,
    var price: String,
    var totalPrice:String
)