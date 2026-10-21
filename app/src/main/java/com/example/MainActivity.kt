package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.data.repository.UserFirestoreRepository
import com.example.ui.navigation.AniFlowMainScreen
import com.example.ui.news.NewsViewModel
import com.example.ui.schedule.ScheduleViewModel
import com.example.ui.theme.AniFlowTheme
import com.example.util.AuthManager

class MainActivity : ComponentActivity() {

    private val newsViewModel: NewsViewModel by viewModels {
        (application as AniFlowApplication).container.viewModelFactory
    }

    private val scheduleViewModel: ScheduleViewModel by viewModels {
        (application as AniFlowApplication).container.viewModelFactory
    }

    private val authManager by lazy {
        AuthManager(this)
    }

    private val firestoreRepository by lazy {
        UserFirestoreRepository(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AniFlowTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AniFlowMainScreen(
                        newsViewModel = newsViewModel,
                        scheduleViewModel = scheduleViewModel,
                        authManager = authManager,
                        firestoreRepository = firestoreRepository
                    )
                }
            }
        }
    }
}
