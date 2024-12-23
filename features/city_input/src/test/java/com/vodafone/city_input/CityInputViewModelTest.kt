package com.vodafone.city_input

import com.vodafone.city_input.presentation.CityInputViewModel
import com.vodafone.core.domain.usecase.GetLastSearchedWeatherUseCase
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CityInputViewModelTest {

    private lateinit var viewModel: CityInputViewModel
    private lateinit var getLastSearchedWeatherUseCase: GetLastSearchedWeatherUseCase

    @Before
    fun setup() {
        getLastSearchedWeatherUseCase = mockk()
        viewModel = CityInputViewModel(getLastSearchedWeatherUseCase)
    }

    @Test
    fun `updateCityName updates cityName state`() = runTest {
        // Arrange: Call the updateCityName method
        viewModel.updateCityName("Alex")
        assertEquals("Alex", viewModel.cityName.value)
    }


}
