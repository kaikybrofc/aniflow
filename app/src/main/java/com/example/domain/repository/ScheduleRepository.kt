package com.example.domain.repository

import com.example.domain.model.AnimeRelease
import kotlinx.coroutines.flow.Flow

/**
 * Interface do repositório para o calendário semanal de lançamentos.
 */
interface ScheduleRepository {
    /**
     * Retorna o fluxo de animes lançados para um determinado dia da semana (1 = Seg, 7 = Dom).
     */
    fun getScheduleStream(dayOfWeek: Int): Flow<List<AnimeRelease>>

    /**
     * Atualiza o calendário de lançamentos a partir da API remota e salva no Room.
     */
    suspend fun refreshSchedule(): Result<Unit>
}
