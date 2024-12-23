package com.vodafone.city_input.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.shared_ui.component.MainAppBar
import com.vodafone.shared_ui.component.SearchedWeatherCard

@Composable
fun CityInputScreen(
    onNavigateToWeather: (String) -> Unit,
    viewModel: CityInputViewModel = hiltViewModel()
) {
    val city by viewModel.cityName.collectAsState()
    val lastSearchedWeather by viewModel.lastSearchedWeather.collectAsState()
    var isEmptyError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { MainAppBar() }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                Column(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = city,
                        onValueChange = {
                            isEmptyError = false// Check if input is empty
                            viewModel.updateCityName(it)
                        },
                        label = { Text("Enter City Name") },
                        isError = isEmptyError,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (isEmptyError) {
                        Text(
                            text = "City name cannot be empty",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
                IconButton(onClick = {
                    if (city.isNotBlank()) {
                        onNavigateToWeather(city)
                    } else {
                        isEmptyError = true
                    }

                }) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search Icon"
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            if (lastSearchedWeather != null) {
                SearchedWeatherCard(lastSearchedWeather!!)
            }
        }
    }
}

