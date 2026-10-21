package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.AnimeReleaseDao
import com.example.data.local.dao.NewsDao
import com.example.data.local.entity.AnimeReleaseEntity
import com.example.data.local.entity.NewsEntity

@Database(
    entities = [
        NewsEntity::class,
        AnimeReleaseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AniFlowDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
    abstract fun animeReleaseDao(): AnimeReleaseDao

    companion object {
        private const val DATABASE_NAME = "aniflow_database.db"

        @Volatile
        private var INSTANCE: AniFlowDatabase? = null

        fun getInstance(context: Context): AniFlowDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AniFlowDatabase::class.java,
                    DATABASE_NAME
                )
                .fallbackToDestructiveMigration(dropAllTables = true)
                .build()
                .also { INSTANCE = it }
            }
        }
    }
}
