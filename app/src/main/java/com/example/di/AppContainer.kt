package com.example.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.local.AniFlowDatabase
import com.example.data.remote.api.AniFlowApiService
import com.example.domain.repository.NewsRepository
import com.example.domain.repository.ScheduleRepository
import com.example.ui.news.NewsViewModel
import com.example.ui.schedule.ScheduleViewModel

interface AppContainer {
    val database: AniFlowDatabase
    val apiService: AniFlowApiService
    val newsRepository: NewsRepository
    val scheduleRepository: ScheduleRepository
    val viewModelFactory: ViewModelProvider.Factory
}

class DefaultAppContainer(private val context: Context) : AppContainer {

    override val database: AniFlowDatabase by lazy {
        AppModule.provideDatabase(context)
    }

    private val newsDao by lazy {
        AppModule.provideNewsDao(database)
    }

    private val animeReleaseDao by lazy {
        AppModule.provideAnimeReleaseDao(database)
    }

    private val okHttpClient by lazy {
        AppModule.provideOkHttpClient()
    }

    private val moshi by lazy {
        AppModule.provideMoshi()
    }

    private val retrofit by lazy {
        AppModule.provideRetrofit(okHttpClient, moshi)
    }

    override val apiService: AniFlowApiService by lazy {
        AppModule.provideAniFlowApiService(retrofit)
    }

    override val newsRepository: NewsRepository by lazy {
        AppModule.provideNewsRepository(newsDao, apiService)
    }

    override val scheduleRepository: ScheduleRepository by lazy {
        AppModule.provideScheduleRepository(animeReleaseDao, apiService)
    }

    override val viewModelFactory: ViewModelProvider.Factory by lazy {
        object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                        NewsViewModel(newsRepository) as T
                    }
                    modelClass.isAssignableFrom(ScheduleViewModel::class.java) -> {
                        ScheduleViewModel(scheduleRepository) as T
                    }
                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        }
    }
}
