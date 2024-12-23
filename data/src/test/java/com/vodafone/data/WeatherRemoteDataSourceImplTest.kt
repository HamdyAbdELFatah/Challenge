package com.vodafone.data

import com.vodafone.data.model.City
import com.vodafone.data.model.Forecast
import com.vodafone.data.model.ForecastResponse
import com.vodafone.data.model.Main
import com.vodafone.data.model.WeatherItem
import com.vodafone.data.model.WeatherResponse
import com.vodafone.data.remote.ApiConstants.API_KEY
import com.vodafone.data.remote.WeatherApi
import com.vodafone.data.remote.datasource.WeatherRemoteDataSourceImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class WeatherRemoteDataSourceImplTest {

    private lateinit var weatherApi: WeatherApi
    private lateinit var weatherRemoteDataSource: WeatherRemoteDataSourceImpl

    @Before
    fun setUp() {
        weatherApi = mockk()
        weatherRemoteDataSource = WeatherRemoteDataSourceImpl(weatherApi)
    }

    @Test
    fun `fetchCurrentWeather returns correct data`() = runBlocking {
        // Arrange
        val cityName = "Cairo"
        val mockResponse = Response.success(
            WeatherResponse(
                visibility = 10000,
                timezone = 3600,
                main = Main(temp = 20.0, tempMin = 15.0, seaLevel = 1013),
                weather = listOf(WeatherItem(icon = "01d", description = "clear sky", main = "Clear", id = 800)),
                name = "Cairo"
            )
        )

        coEvery { weatherApi.getCurrentWeather(cityName, API_KEY) } returns mockResponse

        // Act
        val result = weatherRemoteDataSource.fetchCurrentWeather(cityName)

        // Assert
        assertEquals(mockResponse, result)
        result.body()?.main?.temp?.let { assertEquals(20.0, it, 0.0) }
        assertEquals("Cairo", result.body()?.name)
    }

    @Test
    fun `fetchForecast returns correct data`(): Unit = runBlocking {
        // Arrange
        val cityName = "Cairo"
        val mockForecast = ForecastResponse(
            city = City(country = "Egypt", name = "Cairo"),
            cnt = 7,
            cod = "200",
            message = 0,
            list = listOf(
                Forecast(
                    visibility = 10000,
                    dtTxt = "2024-01-01 12:00:00",
                    weather = listOf(WeatherItem(icon = "10d", description = "light rain", main = "Rain", id = 500)),
                    main = Main(temp = 12.0, tempMin = 10.0, seaLevel = 1015)
                )
            )
        )

        val mockResponse = Response.success(mockForecast)

        coEvery { weatherApi.getForecast(cityName, API_KEY) } returns mockResponse

        // Act
        val result = weatherRemoteDataSource.fetchForecast(cityName)

        // Assert
        assertEquals(mockResponse, result)
        assertEquals("Cairo", result.body()?.city?.name)
        result.body()?.list?.first()?.main?.temp?.let { assertEquals(12.0, it, 0.0) }
    }

}

