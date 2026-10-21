package com.example.data.repository

import com.example.data.local.dao.NewsDao
import com.example.data.local.entity.NewsEntity
import com.example.data.remote.api.AniFlowApiService
import com.example.data.remote.mock.DefaultAnimeDataProvider
import com.example.domain.model.NewsArticle
import com.example.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Implementação offline-first do repositório de notícias.
 * Os dados são sempre observados a partir da fonte da verdade local (Room Database).
 * Chamadas de rede atualizam o cache local de forma transparente.
 */
class NewsRepositoryImpl(
    private val newsDao: NewsDao,
    private val apiService: AniFlowApiService
) : NewsRepository {

    override fun getNewsStream(category: String?): Flow<List<NewsArticle>> {
        val flow = if (category.isNullOrBlank() || category.equals("Todos", ignoreCase = true)) {
            newsDao.getAllNewsFlow()
        } else {
            newsDao.getNewsByCategoryFlow(category)
        }
        return flow.map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun refreshNews(): Result<Unit> {
        return runCatching {
            try {
                val response = apiService.getNews(limit = 30)
                if (response.isSuccessful && response.body()?.data != null) {
                    val dtos = response.body()!!.data!!
                    val entities = dtos.map { dto ->
                        NewsEntity.fromDomain(dto.toDomain())
                    }
                    newsDao.syncArticles(entities)
                    return@runCatching
                }
            } catch (_: Exception) {
                // Fallback para ambiente offline ou indisponibilidade de endpoint remoto
            }

            // Garante que o banco local tenha dados populados no primeiro uso
            val existing = newsDao.getAllNewsFlow().first()
            if (existing.isEmpty()) {
                val initialArticles = DefaultAnimeDataProvider.getDefaultNewsArticles()
                val initialEntities = initialArticles.map { NewsEntity.fromDomain(it) }
                newsDao.insertAll(initialEntities)
            }
        }
    }

    override suspend fun toggleBookmark(articleId: String) {
        newsDao.toggleBookmark(articleId)
    }

    override suspend fun getArticleById(articleId: String): NewsArticle? {
        return newsDao.getNewsById(articleId)?.toDomain()
    }
}
