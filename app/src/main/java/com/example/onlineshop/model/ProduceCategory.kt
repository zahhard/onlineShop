package com.example.onlineshop.model

import com.squareup.moshi.Json

data class ProduceCategory (
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
        )
