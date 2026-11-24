package com.androidquest.weathermate.data.viewModel

import com.androidquest.weathermate.model.CurrentWeather
import com.androidquest.weathermate.model.DailyForecast

/**
 * Contrato do repositório. A UI e o ViewModel só conhecem esta interface,
 * por isso trocar o mock por uma API real (Retrofit) é só mudar a implementação.
 */
interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): CurrentWeather
    suspend fun getForecast(city: String): List<DailyForecast>
}
