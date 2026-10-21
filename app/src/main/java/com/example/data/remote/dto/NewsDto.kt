package com.example.data.remote.dto

import com.example.domain.model.NewsArticle
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponseDto(
    @Json(name = "status") val status: String? = null,
    @Json(name = "data") val data: List<NewsDto>? = null
)

@JsonClass(generateAdapter = true)
data class NewsDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "author") val author: String?,
    @Json(name = "source") val source: String?,
    @Json(name = "published_at") val publishedAtMillis: Long?,
    @Json(name = "category") val category: String?,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "article_url") val articleUrl: String?,
    @Json(name = "summary") val summary: String?
) {
    fun toDomain(): NewsArticle = NewsArticle(
        id = id,
        title = title,
        author = author ?: "AniFlow News",
        source = source ?: "Anime Global",
        publishedAtMillis = publishedAtMillis ?: System.currentTimeMillis(),
        category = category ?: "Anime",
        imageUrl = imageUrl ?: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=800",
        articleUrl = articleUrl ?: "https://myanimelist.net/news",
        summary = summary ?: ""
    )
}
