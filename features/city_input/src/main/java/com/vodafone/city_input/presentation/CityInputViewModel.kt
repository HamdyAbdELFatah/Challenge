package com.vodafone.city_input.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vodafone.core.domain.usecase.GetLastSearchedWeatherUseCase
import com.vodafone.core.models.Weather
import com.vodafone.core.utils.DataResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityInputViewModel @Inject constructor(
    private val getLastSearchedWeatherUseCase: GetLastSearchedWeatherUseCase
) : ViewModel() {

    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName.asStateFlow()


    private val _lastSearchedWeather = MutableStateFlow<Weather?>(null)
    val lastSearchedWeather: StateFlow<Weather?> = _lastSearchedWeather.asStateFlow()

    init {
        getLastSearchedWeather()
    }

    fun updateCityName(city: String) {
        _cityName.value = city
    }


    private fun getLastSearchedWeather() = viewModelScope.launch {
        getLastSearchedWeatherUseCase().collect { result ->
            when (result) {
                is DataResult.Success -> {
                    _cityName.value = result.data?.city ?: ""
                    _lastSearchedWeather.value = result.data
                }
                else -> {  }
            }
        }
    }
}


