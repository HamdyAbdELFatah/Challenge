package com.vodafone.data

import com.vodafone.data.local.WeatherDao
import com.vodafone.data.local.datasource.WeatherLocalDataSourceImpl
import com.vodafone.data.local.entity.WeatherEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherLocalDataSourceImplTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var localDataSource: WeatherLocalDataSourceImpl

    @Before
    fun setUp() {
        weatherDao = mockk()
        localDataSource = WeatherLocalDataSourceImpl(weatherDao)
    }

    @Test
    fun `getLastWeather should return weather entity from DAO`() = runTest {
        val mockWeatherEntity = WeatherEntity(
            cityName = "Cairo",
            currentTemperature = 25.0,
            condition = "Sunny",
            icon = "01d",
            lastUpdated = 123456789L
        )

        // Mock the DAO response
        coEvery { weatherDao.getLastWeather() } returns mockWeatherEntity

        // Call the method
        val result = localDataSource.getLastWeather()

        // Verify the interaction and assert the result
        coVerify { weatherDao.getLastWeather() }
        assertEquals(mockWeatherEntity, result)
    }

    @Test
    fun `saveWeather should save weather entity via DAO`() = runTest {
        val mockWeatherEntity = WeatherEntity(
            cityName = "Cairo",
            currentTemperature = 25.0,
            condition = "Sunny",
            icon = "01d",
            lastUpdated = 123456789L
        )

        // Mock the DAO save operation
        coEvery { weatherDao.saveWeather(any()) } returns Unit

        // Call the method
        localDataSource.saveWeather(mockWeatherEntity)

        // Verify the interaction
        coVerify { weatherDao.saveWeather(mockWeatherEntity) }
    }
}
