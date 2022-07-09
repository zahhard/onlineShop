package model

import com.squareup.moshi.Json

data class Customer(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "first_name") val first_name: String,
    @Json(name = "last_name")val last_name: String,
    @Json(name = "email")val email: String,
    @Json(name = "username")val username: String = "",
    @Json(name = "is_paying_customer") val is_paying_customer: Boolean = false,
    @Json(name = "avatar_url")val avatar_url: String = "",

//    val _links: LinksXXXXX,
//    val billing: BillingXXX,
//    val date_created: String,
//    val date_created_gmt: String,
//    val date_modified: String,
//    val date_modified_gmt: String,
//    val meta_data: List<Any>,
//    val role: String,
//    val shipping: ShippingXXX,
)