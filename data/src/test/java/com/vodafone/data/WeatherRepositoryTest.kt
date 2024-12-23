package com.vodafone.data

import com.vodafone.core.models.DailyForecast
import com.vodafone.core.models.WeatherForecast
import com.vodafone.core.utils.DataResult
import com.vodafone.data.local.datasource.WeatherLocalDataSource
import com.vodafone.data.local.entity.WeatherEntity
import com.vodafone.data.model.City
import com.vodafone.data.model.Forecast
import com.vodafone.data.model.ForecastResponse
import com.vodafone.data.model.Main
import com.vodafone.data.model.WeatherItem
import com.vodafone.data.model.WeatherResponse
import com.vodafone.data.remote.datasource.WeatherRemoteDataSource
import com.vodafone.data.repository.WeatherRepositoryImpl
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class WeatherRepositoryImplTest {

    private lateinit var weatherRepository: WeatherRepositoryImpl
    private val remoteDataSource: WeatherRemoteDataSource = mockk()
    private val localDataSource: WeatherLocalDataSource = mockk()

    @Before
    fun setUp() {
        weatherRepository = WeatherRepositoryImpl(remoteDataSource, localDataSource)
    }

    @After
    fun tearDown() {
        clearMocks(remoteDataSource, localDataSource)
    }

    @Test
    fun `getCurrentWeather should return success when local data matches Cairo`() = runTest {
        val city = "Cairo"
        val localWeather = WeatherEntity(
            cityName = city,
            currentTemperature = 30.0,
            condition = "Sunny",
            icon = "01d",
            lastUpdated = System.currentTimeMillis()
        )

        coEvery { localDataSource.getLastWeather() } returns localWeather

        weatherRepository.getCurrentWeather(city).collect { result ->
            if (result is DataResult.Success) {
                assert(result is DataResult.Success)
                assertEquals(city, result.data?.city)
                coVerify { localDataSource.getLastWeather() }
                coVerify(exactly = 0) { remoteDataSource.fetchCurrentWeather(city) }
            }

        }

    }


    @Test
    fun `getForecast should return success when API call succeeds for Cairo`() = runTest {
        // Arrange
        val city = "Cairo"
        val mockCity = City(name = city)
        val weatherItem = WeatherItem(
            icon = "clear_sky_icon",
            description = "Clear sky",
            main = "Clear",
        )
        val forecast = Forecast(
            visibility = 10000,
            weather = listOf(weatherItem),
            main = Main(temp = 25.0)
        )
        val forecastList = listOf(forecast)
        val forecastResponse = ForecastResponse(
            city = mockCity,
            cnt = 7,
            cod = "200",
            list = forecastList
        )
        val mockResponse: Response<ForecastResponse> = Response.success(forecastResponse)

        coEvery { remoteDataSource.fetchForecast(city) } returns mockResponse

        weatherRepository.getForecast(city).collectLatest {
            if (it is DataResult.Success) {
                val successResult = it
                // Validate the city name and country
                assertEquals(city, successResult.data?.city)
                coVerify { remoteDataSource.fetchForecast(city) }
            }
        }

    }


    @Test
    fun `getLastSearchedWeather should return success when local data exists for Alexandria`() =
        runTest {
            val weatherEntity = WeatherEntity(
                cityName = "Alexandria",
                currentTemperature = 25.0,
                condition = "Cloudy",
                icon = "03d",
                lastUpdated = System.currentTimeMillis()
            )

            coEvery { localDataSource.getLastWeather() } returns weatherEntity

            val result = weatherRepository.getLastSearchedWeather().first()

            assert(result is DataResult.Success)
            assertEquals("Alexandria", (result as DataResult.Success).data?.city)
            coVerify { localDataSource.getLastWeather() }
        }

    @Test
    fun `getLastSearchedWeather should return error when no local data exists for Cairo`() =
        runTest {
            coEvery { localDataSource.getLastWeather() } returns null

            val result = weatherRepository.getLastSearchedWeather().first()

            assert(result is DataResult.Error)
            assertEquals("No data found", (result as DataResult.Error).message)
            coVerify { localDataSource.getLastWeather() }
        }
}

