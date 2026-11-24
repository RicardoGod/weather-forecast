package com.androidquest.weathermate.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Campo de pesquisa + lista de resultados. Não guarda estado: recebe a query
 * e os resultados e devolve eventos (estado "levantado" para quem o usa).
 */
@Composable
fun CitySearch(
    query: String,
    onQueryChange: (String) -> Unit,
    results: List<String>,
    onCitySelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = onQueryChange,
                label = { Text("Pesquisar cidade") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = { onQueryChange("") },
                enabled = query.isNotEmpty()
            ) {
                Text("Limpar")
            }
        }

        when {
            results.isEmpty() -> Text(
                text = "Nenhuma cidade encontrada para \"$query\".",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )

            else -> Column(modifier = Modifier.padding(top = 8.dp)) {
                results.forEach { city ->
                    Text(
                        text = city,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCitySelected(city) }
                            .padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}
