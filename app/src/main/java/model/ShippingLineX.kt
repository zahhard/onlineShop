package model

data class ShippingLineX(
    val id: Int,
    val meta_data: List<Any>,
    val method_id: String,
    val method_title: String,
    val taxes: List<Any>,
    val total: String,
    val total_tax: String
)