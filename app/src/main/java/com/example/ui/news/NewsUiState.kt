package com.example.ui.news

import com.example.domain.model.NewsArticle

/**
 * Estados da interface do feed de notícias.
 * Representa os estados essenciais solicitados na especificação técnica:
 * Loading, Success e Error (com suporte a fallback offline em cache).
 */
sealed interface NewsUiState {
    data object Loading : NewsUiState

    data class Success(
        val articles: List<NewsArticle>,
        val isRefreshing: Boolean = false,
        val isOffline: Boolean = false
    ) : NewsUiState

    data class Error(
        val message: String,
        val cachedArticles: List<NewsArticle> = emptyList()
    ) : NewsUiState
}
