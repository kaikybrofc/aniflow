package com.example.domain.model

/**
 * Modelo de domínio representando uma notícia sobre animes, mangás ou cultura pop.
 */
data class NewsArticle(
    val id: String,
    val title: String,
    val author: String,
    val source: String,
    val publishedAtMillis: Long,
    val category: String,
    val imageUrl: String,
    val articleUrl: String,
    val summary: String,
    val isBookmarked: Boolean = false
)
