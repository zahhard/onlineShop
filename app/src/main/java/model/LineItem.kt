package model

import com.squareup.moshi.Json

data class LineItem(
    @Json(name = "product_id")val product_id: Int,
    @Json(name = "quantity") val quantity: Int,
//    val variation_id: Int
)