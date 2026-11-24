package com.androidquest.weathermate.data.viewModel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidquest.weathermate.domain.filterCities
import com.androidquest.weathermate.screen.WeatherScreen



/**
 * Liga o ViewModel ao ecrã e trata dos 3 estados: carregando, erro e sucesso.
 *
 * A query de pesquisa vive aqui (e não no composable da tela), para não se perder
 * quando o estado passa por Loading e a tela é trocada pelo spinner.
 */
@Composable
fun WeatherRoute(viewModel: WeatherViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()
    var query by rememberSaveable { mutableStateOf("") }

    when (val s = state) {
        is WeatherUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is WeatherUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = s.message, style = MaterialTheme.typography.bodyLarge)
                Button(
                    onClick = { viewModel.loadWeather("Lisboa") },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Tentar novamente")
                }
            }
        }

        is WeatherUiState.Success -> {
            WeatherScreen(
                current = s.current,
                forecast = s.forecast,
                query = query,
                onQueryChange = { query = it },
                results = filterCities(viewModel.availableCities, query),
                onCitySelected = { city ->
                    query = ""
                    viewModel.loadWeather(city)
                }
            )
        }
    }
}