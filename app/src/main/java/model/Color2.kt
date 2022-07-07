package model

import com.squareup.moshi.Json

data class Color2(
//    @Json(name = "_links") val _links: LinksXX,
    @Json(name = "count")val count: Int,
//    @Json(name = "description") val description: String,
    @Json(name = "id")val id: Int,
    @Json(name = "menu_order")val menu_order: Int,
    @Json(name = "name")val name: String,
//    @Json(name = "slug")val slug: String
)