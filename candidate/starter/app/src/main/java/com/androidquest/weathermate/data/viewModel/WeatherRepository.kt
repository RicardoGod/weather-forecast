package com.example.weathermate

import com.androidquest.weathermate.model.CurrentWeather
import com.androidquest.weathermate.model.DailyForecast
import kotlinx.coroutines.delay

/**
 * Contrato do repositório. A UI e o ViewModel só conhecem esta interface,
 * por isso trocar o mock por uma API real (Retrofit) é só mudar a implementação.
 */
interface WeatherRepository {
    suspend fun getCurrentWeather(city: String): CurrentWeather
    suspend fun getForecast(city: String): List<DailyForecast>
}

/**
 * Implementação mock: simula a latência de uma requisição de rede
 * e devolve dados fixos.
 */
class MockWeatherRepository(
    private val latencyMs: Long = 1_200L,
    private val shouldFail: Boolean = false // põe true para testar o estado de erro
) : WeatherRepository {

    override suspend fun getCurrentWeather(city: String): CurrentWeather {
        simulateNetwork()
        return CurrentWeather(
            city = city,
            temperature = 22,
            description = "Parcialmente nublado",
            max = 24,
            min = 17
        )
    }

    override suspend fun getForecast(city: String): List<DailyForecast> {
        simulateNetwork()
        return listOf(
            DailyForecast("Ter", "Sol", 24, 17),
            DailyForecast("Qua", "Nuvens", 23, 16),
            DailyForecast("Qui", "Chuva ligeira", 20, 15),
            DailyForecast("Sex", "Sol", 25, 18)
        )
    }

    private suspend fun simulateNetwork() {
        delay(latencyMs)
        if (shouldFail) throw java.io.IOException("Falha ao obter dados do tempo")
    }
}
