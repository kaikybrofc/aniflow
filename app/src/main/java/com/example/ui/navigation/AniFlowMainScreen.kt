package com.example.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.repository.UserFirestoreRepository
import com.example.ui.components.UserProfileBottomSheet
import com.example.ui.news.NewsScreen
import com.example.ui.news.NewsViewModel
import com.example.ui.schedule.ScheduleScreen
import com.example.ui.schedule.ScheduleViewModel
import com.example.util.AuthManager

enum class AniFlowTab(val title: String) {
    NEWS("Notícias"),
    SCHEDULE("Calendário")
}

@Composable
fun AniFlowMainScreen(
    newsViewModel: NewsViewModel,
    scheduleViewModel: ScheduleViewModel,
    authManager: AuthManager,
    firestoreRepository: UserFirestoreRepository,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(AniFlowTab.NEWS) }
    var showProfileSheet by remember { mutableStateOf(false) }

    val currentUser by authManager.currentUser.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        authManager.attemptSilentSignIn(scope)
    }

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                NavigationBarItem(
                    selected = selectedTab == AniFlowTab.NEWS,
                    onClick = { selectedTab = AniFlowTab.NEWS },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == AniFlowTab.NEWS) Icons.Filled.Newspaper else Icons.Outlined.Newspaper,
                            contentDescription = "Feed de Notícias"
                        )
                    },
                    label = {
                        Text(
                            text = AniFlowTab.NEWS.title,
                            fontWeight = if (selectedTab == AniFlowTab.NEWS) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_news")
                )

                NavigationBarItem(
                    selected = selectedTab == AniFlowTab.SCHEDULE,
                    onClick = { selectedTab = AniFlowTab.SCHEDULE },
                    icon = {
                        Icon(
                            imageVector = if (selectedTab == AniFlowTab.SCHEDULE) Icons.Filled.CalendarMonth else Icons.Outlined.CalendarMonth,
                            contentDescription = "Calendário Semanal"
                        )
                    },
                    label = {
                        Text(
                            text = AniFlowTab.SCHEDULE.title,
                            fontWeight = if (selectedTab == AniFlowTab.SCHEDULE) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.testTag("nav_item_schedule")
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                AniFlowTab.NEWS -> NewsScreen(viewModel = newsViewModel)
                AniFlowTab.SCHEDULE -> ScheduleScreen(viewModel = scheduleViewModel)
            }

            // Botão de Perfil / Login Flutuante no Topo Direito
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 10.dp, end = 60.dp)
            ) {
                IconButton(
                    onClick = { showProfileSheet = true },
                    modifier = Modifier.testTag("profile_button")
                ) {
                    if (currentUser != null && !currentUser!!.photoUrl?.toString().isNullOrBlank()) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(currentUser!!.photoUrl)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Perfil do usuário",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Entrar ou Ver Perfil",
                            tint = if (currentUser != null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }

        if (showProfileSheet) {
            UserProfileBottomSheet(
                currentUser = currentUser,
                authManager = authManager,
                firestoreRepository = firestoreRepository,
                onDismiss = { showProfileSheet = false }
            )
        }
    }
}
