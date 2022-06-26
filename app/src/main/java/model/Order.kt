package model

import com.squareup.moshi.Json

data class Order(
    @Json(name = "product_id")val billing: BillingX,
    @Json(name = "product_id")val line_items: List<LineItem>,
//    val payment_method: String,
//    val payment_method_title: String,
//    val set_paid: Boolean,

//    val shipping: ShippingX,
//    val shipping_lines: List<ShippingLine>
)