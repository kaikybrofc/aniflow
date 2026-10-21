package com.example.domain.repository

import com.example.domain.model.NewsArticle
import kotlinx.coroutines.flow.Flow

/**
 * Interface do repositório para acesso e sincronização de notícias.
 * Implementa a estratégia offline-first.
 */
interface NewsRepository {
    /**
     * Retorna um Flow com a lista de notícias locais persistidas no Room.
     * Permite filtro opcional por categoria.
     */
    fun getNewsStream(category: String? = null): Flow<List<NewsArticle>>

    /**
     * Atualiza as notícias a partir do serviço remoto e salva no banco local.
     */
    suspend fun refreshNews(): Result<Unit>

    /**
     * Alterna o status de favorito/salvo de um artigo.
     */
    suspend fun toggleBookmark(articleId: String)

    /**
     * Busca um artigo específico pelo ID.
     */
    suspend fun getArticleById(articleId: String): NewsArticle?
}
