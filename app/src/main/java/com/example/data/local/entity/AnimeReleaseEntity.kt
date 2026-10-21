package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.AnimeRelease
import com.example.domain.model.AnimeReleaseStatus

@Entity(tableName = "anime_releases")
data class AnimeReleaseEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val japaneseTitle: String,
    val imageUrl: String,
    val dayOfWeek: Int,
    val airingTime: String,
    val episodeNumber: Int,
    val status: String,
    val score: Double,
    val genresCsv: String,
    val synopsis: String,
    val cachedAtMillis: Long = System.currentTimeMillis()
) {
    fun toDomain(): AnimeRelease = AnimeRelease(
        id = id,
        title = title,
        japaneseTitle = japaneseTitle,
        imageUrl = imageUrl,
        dayOfWeek = dayOfWeek,
        airingTime = airingTime,
        episodeNumber = episodeNumber,
        status = runCatching { AnimeReleaseStatus.valueOf(status) }.getOrDefault(AnimeReleaseStatus.UPCOMING),
        score = score,
        genres = if (genresCsv.isBlank()) emptyList() else genresCsv.split(",").map { it.trim() },
        synopsis = synopsis
    )

    companion object {
        fun fromDomain(domain: AnimeRelease): AnimeReleaseEntity = AnimeReleaseEntity(
            id = domain.id,
            title = domain.title,
            japaneseTitle = domain.japaneseTitle,
            imageUrl = domain.imageUrl,
            dayOfWeek = domain.dayOfWeek,
            airingTime = domain.airingTime,
            episodeNumber = domain.episodeNumber,
            status = domain.status.name,
            score = domain.score,
            genresCsv = domain.genres.joinToString(","),
            synopsis = domain.synopsis
        )
    }
}
