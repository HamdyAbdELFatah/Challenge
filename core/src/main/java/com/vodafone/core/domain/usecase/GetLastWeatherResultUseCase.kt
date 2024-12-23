package com.vodafone.core.domain.usecase

import com.vodafone.core.domain.repository.WeatherRepository
import com.vodafone.core.models.Weather
import com.vodafone.core.utils.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLastSearchedWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(): Flow<DataResult<Weather>> {
        return repository.getLastSearchedWeather()
    }
}