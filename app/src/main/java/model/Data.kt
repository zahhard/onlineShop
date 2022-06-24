package model

import com.squareup.moshi.Json

data class Data(
    @Json(name = "billing")val billing: Billing = Billing(),
    @Json(name = "email")val email: String ,
    @Json(name = "first_name")val first_name: String,
    @Json(name = "last_name") val last_name: String,
//    @Json(name = "shipping")  val shipping: Shipping = ,
    @Json(name = "username") val username: String = ""
)