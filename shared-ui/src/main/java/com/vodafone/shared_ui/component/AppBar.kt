package com.vodafone.shared_ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(modifier: Modifier = Modifier,title:String="Weather App") {
    TopAppBar(
        title = { Text(title, color = Color(0xFFE60000)) },
        modifier = modifier,
    )

}