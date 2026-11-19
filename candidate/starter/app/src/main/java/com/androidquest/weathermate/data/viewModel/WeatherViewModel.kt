package com.example.weathermate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidquest.weathermate.model.CurrentWeather
import com.androidquest.weathermate.model.DailyForecast
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(
        val current: CurrentWeather,
        val forecast: List<DailyForecast>
    ) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

class WeatherViewModel(
    private val repository: WeatherRepository = MockWeatherRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        loadWeather("Lisboa")
    }

    fun loadWeather(city: String) {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            try {
                // As duas chamadas correm em paralelo
                val current = async { repository.getCurrentWeather(city) }
                val forecast = async { repository.getForecast(city) }
                _uiState.value = WeatherUiState.Success(
                    current = current.await(),
                    forecast = forecast.await()
                )
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(
                    e.message ?: "Erro desconhecido"
                )
            }
        }
    }
}
