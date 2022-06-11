package model

import com.squareup.moshi.Json

data class CommentsItem(
//    val _links: LinksX,
    @Json(name = "date_created") val date_created: String,
//    val date_created_gmt: String,
    @Json(name = "id")val id: Int,
    @Json(name = "product_id")val product_id: Int,
    @Json(name = "rating")val rating: Int,
    @Json(name = "review")val review: String,
    @Json(name = "reviewer")val reviewer: String,
//    val reviewer_avatar_urls: ReviewerAvatarUrls,
//    val reviewer_email: String,
//    val status: String,
//    val verified: Boolean
)