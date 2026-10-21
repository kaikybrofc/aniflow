package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news_articles ORDER BY publishedAtMillis DESC")
    fun getAllNewsFlow(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news_articles WHERE category = :category ORDER BY publishedAtMillis DESC")
    fun getNewsByCategoryFlow(category: String): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news_articles WHERE id = :id LIMIT 1")
    suspend fun getNewsById(id: String): NewsEntity?

    @Query("SELECT * FROM news_articles WHERE isBookmarked = 1 ORDER BY publishedAtMillis DESC")
    fun getBookmarkedNewsFlow(): Flow<List<NewsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<NewsEntity>)

    @Query("UPDATE news_articles SET isBookmarked = CASE WHEN isBookmarked = 1 THEN 0 ELSE 1 END WHERE id = :id")
    suspend fun toggleBookmark(id: String)

    @Query("DELETE FROM news_articles WHERE isBookmarked = 0")
    suspend fun clearNonBookmarked()

    @Transaction
    suspend fun syncArticles(freshArticles: List<NewsEntity>) {
        // Preserva os artigos marcados como favoritos pelo usuário
        insertAll(freshArticles)
    }
}
