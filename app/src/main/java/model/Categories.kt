package model

import android.icu.number.NumberFormatter
import android.provider.ContactsContract
import com.squareup.moshi.Json


data class Category(
    @Json(name = "name") val name: String = "",
    @Json(name = "image") val image: Image ,
    @Json(name = "display") val display: String = "",
)

data class Categories(
    @Json(name = "name") val name: String,
    @Json(name = "src") val src: String,
)

data class Image (
    @Json (name = "src") val src : String
        )