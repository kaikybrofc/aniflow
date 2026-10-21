package com.example.data.repository

import com.example.data.local.dao.AnimeReleaseDao
import com.example.data.local.entity.AnimeReleaseEntity
import com.example.data.remote.api.AniFlowApiService
import com.example.data.remote.mock.DefaultAnimeDataProvider
import com.example.domain.model.AnimeRelease
import com.example.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Implementação offline-first do repositório do calendário semanal de lançamentos.
 */
class ScheduleRepositoryImpl(
    private val animeReleaseDao: AnimeReleaseDao,
    private val apiService: AniFlowApiService
) : ScheduleRepository {

    override fun getScheduleStream(dayOfWeek: Int): Flow<List<AnimeRelease>> {
        return animeReleaseDao.getReleasesByDayFlow(dayOfWeek).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun refreshSchedule(): Result<Unit> {
        return runCatching {
            try {
                val response = apiService.getSchedules()
                if (response.isSuccessful && response.body()?.data != null) {
                    val dtos = response.body()!!.data!!
                    val entities = dtos.map { AnimeReleaseEntity.fromDomain(it.toDomain()) }
                    animeReleaseDao.replaceAll(entities)
                    return@runCatching
                }
            } catch (_: Exception) {
                // Fallback offline
            }

            val existing = animeReleaseDao.getAllReleasesFlow().first()
            if (existing.isEmpty()) {
                val defaultReleases = DefaultAnimeDataProvider.getDefaultAnimeReleases()
                val entities = defaultReleases.map { AnimeReleaseEntity.fromDomain(it) }
                animeReleaseDao.replaceAll(entities)
            }
        }
    }
}
