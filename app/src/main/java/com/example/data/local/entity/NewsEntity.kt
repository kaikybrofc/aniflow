package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.NewsArticle

@Entity(tableName = "news_articles")
data class NewsEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val source: String,
    val publishedAtMillis: Long,
    val category: String,
    val imageUrl: String,
    val articleUrl: String,
    val summary: String,
    val isBookmarked: Boolean = false,
    val cachedAtMillis: Long = System.currentTimeMillis()
) {
    fun toDomain(): NewsArticle = NewsArticle(
        id = id,
        title = title,
        author = author,
        source = source,
        publishedAtMillis = publishedAtMillis,
        category = category,
        imageUrl = imageUrl,
        articleUrl = articleUrl,
        summary = summary,
        isBookmarked = isBookmarked
    )

    companion object {
        fun fromDomain(domain: NewsArticle): NewsEntity = NewsEntity(
            id = domain.id,
            title = domain.title,
            author = domain.author,
            source = domain.source,
            publishedAtMillis = domain.publishedAtMillis,
            category = domain.category,
            imageUrl = domain.imageUrl,
            articleUrl = domain.articleUrl,
            summary = domain.summary,
            isBookmarked = domain.isBookmarked
        )
    }
}
