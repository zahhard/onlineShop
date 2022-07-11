package com.example.onlineshop.model

import com.squareup.moshi.Json

data class Data(

    @Json(name = "email") val email: String,
    @Json(name = "first_name")  val first_name: String,
    @Json(name = "last_name") val last_name: String,
//    val shipping: Shipping,
//    val username: String
)