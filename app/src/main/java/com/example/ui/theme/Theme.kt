package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AniPurpleLight,
    onPrimary = Color(0xFF1E1035),
    primaryContainer = AniPurpleDark,
    onPrimaryContainer = Color(0xFFEDE9FE),
    secondary = AniCoralLight,
    onSecondary = Color(0xFF4C0519),
    secondaryContainer = AniCoralDark,
    onSecondaryContainer = Color(0xFFFFE4E6),
    tertiary = AniCyanLight,
    onTertiary = Color(0xFF083344),
    background = DarkBackground,
    onBackground = Color(0xFFF1F0F7),
    surface = DarkSurface,
    onSurface = Color(0xFFF1F0F7),
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFD4CEE8),
    outline = DarkOutline
)

private val LightColorScheme = lightColorScheme(
    primary = AniPurpleDark,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE9FE),
    onPrimaryContainer = Color(0xFF4C1D95),
    secondary = AniCoralDark,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE4E6),
    onSecondaryContainer = Color(0xFF881337),
    tertiary = AniCyanTertiary,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = Color(0xFF161226),
    surface = LightSurface,
    onSurface = Color(0xFF161226),
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF4C4760),
    outline = LightOutline
)

@Composable
fun AniFlowTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Pinned custom AniFlow anime palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
