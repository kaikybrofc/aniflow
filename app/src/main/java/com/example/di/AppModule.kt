package com.example.di

import android.content.Context
import com.example.data.local.AniFlowDatabase
import com.example.data.local.dao.AnimeReleaseDao
import com.example.data.local.dao.NewsDao
import com.example.data.remote.api.AniFlowApiService
import com.example.data.repository.NewsRepositoryImpl
import com.example.data.repository.ScheduleRepositoryImpl
import com.example.domain.repository.NewsRepository
import com.example.domain.repository.ScheduleRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Módulo de injeção de dependência provendo as instâncias singleton
 * de Room, Retrofit, OkHttp, Moshi, DAOs e Repositórios da Clean Architecture.
 */
object AppModule {

    private const val BASE_URL = "https://api.aniflow.example.com/"

    fun provideDatabase(context: Context): AniFlowDatabase {
        return AniFlowDatabase.getInstance(context)
    }

    fun provideNewsDao(database: AniFlowDatabase): NewsDao {
        return database.newsDao()
    }

    fun provideAnimeReleaseDao(database: AniFlowDatabase): AnimeReleaseDao {
        return database.animeReleaseDao()
    }

    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun provideAniFlowApiService(retrofit: Retrofit): AniFlowApiService {
        return retrofit.create(AniFlowApiService::class.java)
    }

    fun provideNewsRepository(
        newsDao: NewsDao,
        apiService: AniFlowApiService
    ): NewsRepository {
        return NewsRepositoryImpl(newsDao, apiService)
    }

    fun provideScheduleRepository(
        animeReleaseDao: AnimeReleaseDao,
        apiService: AniFlowApiService
    ): ScheduleRepository {
        return ScheduleRepositoryImpl(animeReleaseDao, apiService)
    }
}
