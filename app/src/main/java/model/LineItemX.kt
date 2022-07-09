package model

import com.squareup.moshi.Json

data class LineItemX(
    @Json(name = "id")val id: Int,
//    @Json(name = "billing")val meta_data: List<MetaDataX>,
//    @Json(name = "name")val name: String,
    @Json(name = "price")val price: Int,
    @Json(name = "product_id")val product_id: Int,
    @Json(name = "quantity")val quantity: Int,
//    @Json(name = "billing")val sku: String,
//    @Json(name = "billing") val subtotal: String,
//    @Json(name = "billing")val subtotal_tax: String,
//    @Json(name = "billing")val tax_class: String,
//    @Json(name = "billing") val taxes: List<Taxe>,
    @Json(name = "total") val total: String,
//    @Json(name = "billing") val total_tax: String,
//    @Json(name = "billing") val variation_id: Int
)