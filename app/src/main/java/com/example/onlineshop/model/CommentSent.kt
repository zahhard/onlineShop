package com.example.onlineshop.model

import com.squareup.moshi.Json

data class CommentSent(@Json(name = "product_id")val product_id: Int,
                       @Json(name = "review")val review: String,
                       @Json(name = "reviewer")val reviewer: String,
                       @Json(name = "reviewer_email") val reviewer_email: String,
                       @Json(name = "rating")val rating: String,
                       )
