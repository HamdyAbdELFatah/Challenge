package com.vodafone.forecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.vodafone.forecast.navigation.AppNavigationHost
import com.vodafone.forecast.ui.theme.ForecastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForecastTheme {
                val navController = rememberNavController()
                AppNavigationHost(navController)
            }
        }
    }
}
