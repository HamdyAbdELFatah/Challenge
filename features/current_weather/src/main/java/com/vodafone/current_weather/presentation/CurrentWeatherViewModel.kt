package com.vodafone.current_weather.presentation;

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.core.domain.usecase.GetWeatherUseCase
import com.vodafone.core.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetWeatherUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CurrentWeatherState>(CurrentWeatherState.Idle)
    val state: StateFlow<CurrentWeatherState> = _state.asStateFlow()

    fun fetchCurrentWeather(city: String) {
        viewModelScope.launch {
            getCurrentWeatherUseCase(city).collect { result ->
                _state.value = when (result) {
                    is DataResult.Loading -> CurrentWeatherState.Loading
                    is DataResult.Success -> CurrentWeatherState.Success(result.data)
                    is DataResult.Error -> CurrentWeatherState.Error(result.message ?: "Unknown error")
                }
            }
        }
    }
}
