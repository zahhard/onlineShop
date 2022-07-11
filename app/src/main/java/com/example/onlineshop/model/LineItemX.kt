package com.example.onlineshop.model

data class LineItemX(
    val id: Int,
    val meta_data: List<MetaDataX>,
    val name: String,
    val price: Int,
    val product_id: Int,
    val quantity: Int,
    val sku: String,
    val subtotal: String,
    val subtotal_tax: String,
    val tax_class: String,
    val taxes: List<Taxe>,
    val total: String,
    val total_tax: String,
    val variation_id: Int
)