package com.example.onlineshop.model

import android.icu.number.NumberFormatter
import android.provider.ContactsContract
import com.squareup.moshi.Json


data class Category(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String = "",
    @Json(name = "image") val image: com.example.onlineshop.model.Image,
    @Json(name = "display") val display: String = "",
)

data class Categories(
    @Json(name = "name") val name: String,
    @Json(name = "src") val src: String,
)

data class Image (
    @Json (name = "src") val src : String
    )