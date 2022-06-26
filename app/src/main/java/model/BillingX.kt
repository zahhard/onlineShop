package model

import com.squareup.moshi.Json

data class BillingX(
    @Json(name = "address_1")  val address_1: String,
//    val address_2: String,
    @Json(name = "city")val city: String,
    @Json(name = "country") val country: String,
    @Json(name = "email") val email: String,
//    val first_name: String,
//    val last_name: String,
//    val phone: String,
//    val postcode: String,
//    val state: String
)