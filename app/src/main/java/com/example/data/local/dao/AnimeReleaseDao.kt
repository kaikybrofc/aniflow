package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.entity.AnimeReleaseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeReleaseDao {
    @Query("SELECT * FROM anime_releases WHERE dayOfWeek = :dayOfWeek ORDER BY airingTime ASC")
    fun getReleasesByDayFlow(dayOfWeek: Int): Flow<List<AnimeReleaseEntity>>

    @Query("SELECT * FROM anime_releases ORDER BY dayOfWeek ASC, airingTime ASC")
    fun getAllReleasesFlow(): Flow<List<AnimeReleaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(releases: List<AnimeReleaseEntity>)

    @Query("DELETE FROM anime_releases")
    suspend fun clearAll()

    @Transaction
    suspend fun replaceAll(releases: List<AnimeReleaseEntity>) {
        clearAll()
        insertAll(releases)
    }
}
