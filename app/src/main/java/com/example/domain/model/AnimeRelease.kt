package com.example.domain.model

/**
 * Modelo de domínio representando um lançamento semanal de anime.
 * dayOfWeek: 1 = Segunda-feira, ..., 7 = Domingo (padrão ISO-8601).
 */
data class AnimeRelease(
    val id: String,
    val title: String,
    val japaneseTitle: String,
    val imageUrl: String,
    val dayOfWeek: Int,
    val airingTime: String,
    val episodeNumber: Int,
    val status: AnimeReleaseStatus,
    val score: Double,
    val genres: List<String>,
    val synopsis: String
)

enum class AnimeReleaseStatus(val label: String) {
    AIRING_TODAY("Lançado hoje"),
    UPCOMING("Em breve"),
    COMPLETED("Exibido")
}
