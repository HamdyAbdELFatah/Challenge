package com.vodafone.forecast

import com.vodafone.core.models.DailyForecast
import com.vodafone.core.models.WeatherForecast
import com.vodafone.core.utils.DataResult
import com.vodafone.forecast.presentation.ForecastIntent
import com.vodafone.forecast.presentation.ForecastState
import com.vodafone.forecast.presentation.ForecastViewModel
import com.vodafone.forecast.usecase.GetForecastUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class ForecastViewModelTest {

    private val getForecastUseCase: GetForecastUseCase = mockk()
    private lateinit var viewModel: ForecastViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher()) // Set the Main dispatcher for testing
        viewModel = ForecastViewModel(getForecastUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the Main dispatcher after the test
    }

    @Test
    fun `fetchForecast emits Success state`() = runTest {
        // Arrange
        val city = "Cairo"
        val forecastData = WeatherForecast(
            city = city,
            country = "Egypt",
            forecasts = listOf(
                DailyForecast(
                    dateTime = "2024-12-23T12:00:00",
                    temperature = 25.0,
                    weatherCondition = "Sunny",
                    weatherDescription = "Clear sky",
                    icon = "01d"
                )
            )
        )

        coEvery { getForecastUseCase(city) } returns flow {
            emit(DataResult.Success(forecastData))
        }

        viewModel.handleIntent(ForecastIntent.FetchForecast(city))
        advanceUntilIdle()
        // Assert
        assertEquals(
            ForecastState.Success(forecastData),
            viewModel.state.value
        )
    }
}

