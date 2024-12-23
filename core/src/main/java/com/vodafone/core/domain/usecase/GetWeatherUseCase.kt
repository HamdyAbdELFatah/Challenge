package com.vodafone.core.domain.usecase

import com.vodafone.core.domain.repository.WeatherRepository
import com.vodafone.core.models.Weather
import com.vodafone.core.utils.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    operator fun invoke(city: String): Flow<DataResult<Weather>> {
        return repository.getCurrentWeather(city)
    }
}