package com.example.data.remote.dto

import com.example.domain.model.AnimeRelease
import com.example.domain.model.AnimeReleaseStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ScheduleResponseDto(
    @Json(name = "status") val status: String? = null,
    @Json(name = "data") val data: List<ScheduleDto>? = null
)

@JsonClass(generateAdapter = true)
data class ScheduleDto(
    @Json(name = "id") val id: String,
    @Json(name = "title") val title: String,
    @Json(name = "japanese_title") val japaneseTitle: String?,
    @Json(name = "image_url") val imageUrl: String?,
    @Json(name = "day_of_week") val dayOfWeek: Int,
    @Json(name = "airing_time") val airingTime: String?,
    @Json(name = "episode_number") val episodeNumber: Int?,
    @Json(name = "status") val status: String?,
    @Json(name = "score") val score: Double?,
    @Json(name = "genres") val genres: List<String>?,
    @Json(name = "synopsis") val synopsis: String?
) {
    fun toDomain(): AnimeRelease = AnimeRelease(
        id = id,
        title = title,
        japaneseTitle = japaneseTitle ?: "",
        imageUrl = imageUrl ?: "https://images.unsplash.com/photo-1578632767115-351597cf2477?w=800",
        dayOfWeek = dayOfWeek,
        airingTime = airingTime ?: "12:00",
        episodeNumber = episodeNumber ?: 1,
        status = when (status?.uppercase()) {
            "AIRING_TODAY", "AIRING" -> AnimeReleaseStatus.AIRING_TODAY
            "COMPLETED" -> AnimeReleaseStatus.COMPLETED
            else -> AnimeReleaseStatus.UPCOMING
        },
        score = score ?: 8.0,
        genres = genres ?: emptyList(),
        synopsis = synopsis ?: ""
    )
}
