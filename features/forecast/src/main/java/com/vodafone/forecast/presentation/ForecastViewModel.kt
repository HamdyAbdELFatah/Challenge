package com.vodafone.forecast.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.core.models.WeatherForecast
import com.vodafone.core.utils.DataResult
import com.vodafone.forecast.usecase.GetForecastUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val getForecastUseCase: GetForecastUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<ForecastState>(ForecastState.Idle)
    val state: StateFlow<ForecastState> = _state.asStateFlow()

    fun handleIntent(intent: ForecastIntent) {
        when (intent) {
            is ForecastIntent.FetchForecast -> fetchForecast(intent.city)
        }
    }

    private fun fetchForecast(city: String) {
        viewModelScope.launch {
            getForecastUseCase(city).collect { result ->
                _state.value = reduce(result)
            }
        }
    }

    private fun reduce(result: DataResult<WeatherForecast?>): ForecastState {
        return when (result) {
            is DataResult.Loading -> ForecastState.Loading
            is DataResult.Success -> ForecastState.Success(result.data)
            is DataResult.Error -> ForecastState.Error(result.message ?: "Unknown error")
        }
    }
}
