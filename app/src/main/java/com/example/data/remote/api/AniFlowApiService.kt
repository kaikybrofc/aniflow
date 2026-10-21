package com.example.data.remote.api

import com.example.data.remote.dto.NewsResponseDto
import com.example.data.remote.dto.ScheduleResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AniFlowApiService {

    @GET("v1/anime/news")
    suspend fun getNews(
        @Query("limit") limit: Int = 20,
        @Query("category") category: String? = null
    ): Response<NewsResponseDto>

    @GET("v1/anime/schedules")
    suspend fun getSchedules(
        @Query("day") dayOfWeek: Int? = null
    ): Response<ScheduleResponseDto>
}
