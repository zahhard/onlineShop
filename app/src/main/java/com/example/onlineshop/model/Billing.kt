package com.example.onlineshop.model

import com.squareup.moshi.Json

data class Billing(
    @Json(name = "address_1") val address_1: String ="",
//    @Json(name = "address_2")   val address_2: String ="",
//    @Json(name = "city")  val city: String = "",
//    @Json(name = "company")  val company: String ="",
//    @Json(name = "country") val country: String= "",
//    @Json(name = "email")  val email: String="",
//    @Json(name = "first_name")  val first_name: String="",
//    @Json(name = "last_name") val last_name: String="",
//    @Json(name = "phone") val phone: String="",
//    @Json(name = "postcode") val postcode: String="",
//    @Json(name = "state") val state: String=""
)