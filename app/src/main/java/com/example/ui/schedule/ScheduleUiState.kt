package com.example.ui.schedule

import com.example.domain.model.AnimeRelease

sealed interface ScheduleUiState {
    data object Loading : ScheduleUiState

    data class Success(
        val releases: List<AnimeRelease>,
        val isRefreshing: Boolean = false,
        val selectedDay: Int = 1
    ) : ScheduleUiState

    data class Error(
        val message: String
    ) : ScheduleUiState
}
