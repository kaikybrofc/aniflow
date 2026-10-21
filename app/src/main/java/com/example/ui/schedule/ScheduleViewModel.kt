package com.example.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.AnimeRelease
import com.example.domain.repository.ScheduleRepository
import com.example.util.DateUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _selectedDay = MutableStateFlow(DateUtils.getCurrentIsoDayOfWeek())
    val selectedDay: StateFlow<Int> = _selectedDay.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _selectedAnime = MutableStateFlow<AnimeRelease?>(null)
    val selectedAnime: StateFlow<AnimeRelease?> = _selectedAnime.asStateFlow()

    val uiState: StateFlow<ScheduleUiState> = _selectedDay
        .flatMapLatest { day ->
            scheduleRepository.getScheduleStream(day)
        }
        .combine(_isRefreshing) { releases, refreshing ->
            if (releases.isEmpty() && !refreshing) {
                ScheduleUiState.Loading
            } else {
                ScheduleUiState.Success(
                    releases = releases,
                    isRefreshing = refreshing,
                    selectedDay = _selectedDay.value
                )
            }
        }
        .catch { error ->
            emit(ScheduleUiState.Error(error.localizedMessage ?: "Erro ao carregar calendário"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ScheduleUiState.Loading
        )

    init {
        refreshSchedule()
    }

    fun selectDay(day: Int) {
        if (day in 1..7) {
            _selectedDay.value = day
        }
    }

    fun refreshSchedule() {
        viewModelScope.launch {
            _isRefreshing.value = true
            scheduleRepository.refreshSchedule()
            _isRefreshing.value = false
        }
    }

    fun openAnimeDetails(anime: AnimeRelease) {
        _selectedAnime.value = anime
    }

    fun dismissAnimeDetails() {
        _selectedAnime.value = null
    }
}
