package com.vodafone.forecast.usecase

import com.vodafone.core.domain.repository.WeatherRepository
import com.vodafone.core.models.WeatherForecast
import com.vodafone.core.utils.DataResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): Flow<DataResult<WeatherForecast?>> {
        return repository.getForecast(city)
    }
}
