package com.vodafone.forecast.ui.theme

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
    primary = Color(0xFFB20000), // A darker shade of Vodafone red for dark theme
    secondary = Color(0xFF757575), // Lighter grey for dark theme
    background = Color(0xFF121212), // Dark background color
    surface = Color(0xFF1E1E1E), // Dark surface color
    onPrimary = Color.White, // Text on primary color
    onSecondary = Color.White, // Text on secondary color
    onBackground = Color.White, // Text on background
    onSurface = Color.White // Text on surface


)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE60000), // Vodafone red
    secondary = Color(0xFF9E9E9E), // Grey for secondary elements
    background = Color(0xFFF1F1F1), // Light background color
    surface = Color(0xFFFFFFFF), // White surface
    onPrimary = Color.White, // Text on primary color
    onSecondary = Color.Black, // Text on secondary color
    onBackground = Color.Black, // Text on background
    onSurface = Color.Black // Text on surface
)

@Composable
fun ForecastTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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