package com.vodafone.data

import com.vodafone.core.domain.repository.WeatherRepository
import com.vodafone.core.utils.DataResult
import com.vodafone.data.local.datasource.WeatherLocalDataSource
import com.vodafone.data.local.entity.WeatherEntity
import com.vodafone.data.mapper.toWeatherDomainModel
import com.vodafone.data.mapper.toWeatherEntity
import com.vodafone.data.model.Main
import com.vodafone.data.model.WeatherResponse
import com.vodafone.data.remote.datasource.WeatherRemoteDataSource
import com.vodafone.data.repository.WeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

import io.mockk.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class WeatherRepositoryTest {

    private lateinit var weatherRepository: WeatherRepository
    private val remoteDataSource: WeatherRemoteDataSource = mockk()
    private val localDataSource: WeatherLocalDataSource = mockk()

    @Before
    fun setup() {
        weatherRepository = WeatherRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getCurrentWeather fetches from remote and saves to local`() = runBlocking {
        // Arrange
        val cityName = "Cairo"
        val mockResponse = WeatherResponse(name = cityName, main = Main(temp = 25.0))
        val mockWeatherEntity = mockResponse.toWeatherEntity()
        val expectedWeather = mockResponse.toWeatherDomainModel()

        coEvery { remoteDataSource.fetchCurrentWeather(cityName) } returns Response.success(mockResponse)
        coEvery { localDataSource.saveWeather(any()) } just Runs

        // Act
        val result = weatherRepository.getCurrentWeather(cityName)

        // Assert
        result.collect { dataResult ->
            when (dataResult) {
                is DataResult.Success -> {
                    assertEquals(expectedWeather, dataResult.data)
                }
                else -> throw AssertionError("Expected success but got $dataResult")
            }
        }

        coVerify { remoteDataSource.fetchCurrentWeather(cityName) }
        coVerify { localDataSource.saveWeather(mockWeatherEntity) }
    }

    @Test
    fun `getLastSearchedWeather returns last saved weather`() = runBlocking {
        // Arrange
        val mockWeatherEntity = WeatherEntity(
            cityName = "Cairo",
            currentTemperature = 25.0,
            condition = "Sunny",
            icon = "sun_icon",
            lastUpdated = System.currentTimeMillis()
        )
        val expectedWeather = mockWeatherEntity.toWeatherDomainModel()

        coEvery { localDataSource.getLastWeather() } returns mockWeatherEntity

        // Act
        val result = weatherRepository.getLastSearchedWeather()

        // Assert
        result.collect { dataResult ->
            when (dataResult) {
                is DataResult.Success -> {
                    assertEquals(expectedWeather, dataResult.data)
                }
                else -> throw AssertionError("Expected success but got $dataResult")
            }
        }

        coVerify { localDataSource.getLastWeather() }
    }
}

