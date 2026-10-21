package com.example.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.NewsArticle
import com.example.domain.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel responsável por gerenciar o estado do Feed de Notícias.
 * Observa o fluxo offline-first persistido no Room e dispara sincronizações via rede.
 */
@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _selectedCategory = MutableStateFlow("Todos")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _selectedArticleForReading = MutableStateFlow<NewsArticle?>(null)
    val selectedArticleForReading: StateFlow<NewsArticle?> = _selectedArticleForReading.asStateFlow()

    // O fluxo reage dinamicamente à categoria selecionada e à busca textual
    val uiState: StateFlow<NewsUiState> = _selectedCategory
        .flatMapLatest { category ->
            newsRepository.getNewsStream(category)
        }
        .combine(_searchQuery) { articles, query ->
            if (query.isBlank()) {
                articles
            } else {
                articles.filter { article ->
                    article.title.contains(query, ignoreCase = true) ||
                    article.summary.contains(query, ignoreCase = true) ||
                    article.author.contains(query, ignoreCase = true)
                }
            }
        }
        .combine(_isRefreshing) { articles, refreshing ->
            if (articles.isEmpty() && !refreshing) {
                // Caso o banco esteja vazio no início, o repositório iniciará sincronização
                NewsUiState.Loading
            } else {
                NewsUiState.Success(
                    articles = articles,
                    isRefreshing = refreshing
                )
            }
        }
        .catch { throwable ->
            emit(NewsUiState.Error(message = throwable.localizedMessage ?: "Erro ao carregar notícias"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NewsUiState.Loading
        )

    init {
        // Dispara a sincronização inicial ao instanciar o ViewModel
        refreshNews()
    }

    /**
     * Executa a atualização remota (Pull-to-refresh ou inicial)
     */
    fun refreshNews() {
        viewModelScope.launch {
            _isRefreshing.value = true
            newsRepository.refreshNews()
            _isRefreshing.value = false
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleBookmark(articleId: String) {
        viewModelScope.launch {
            newsRepository.toggleBookmark(articleId)
        }
    }

    fun openArticle(article: NewsArticle) {
        _selectedArticleForReading.value = article
    }

    fun dismissArticleReader() {
        _selectedArticleForReading.value = null
    }
}
