package com.androidquest.weathermate.domain

import java.text.Normalizer

/**
 * Filtra cidades pelo texto digitado.
 * - Ignora maiúsculas/minúsculas e acentos ("evora" encontra "Évora").
 * - Correspondência parcial: "por" encontra "Porto".
 * - Query vazia (ou só espaços) devolve a lista completa.
 *
 * Função pura, sem Compose: pode ser testada sem emulador.
 */
fun filterCities(cities: List<String>, query: String): List<String> {
    val normalizedQuery = query.normalizeForSearch()
    if (normalizedQuery.isEmpty()) return cities
    return cities.filter { it.normalizeForSearch().contains(normalizedQuery) }
}

private fun String.normalizeForSearch(): String =
    Normalizer.normalize(trim(), Normalizer.Form.NFD)
        .replace(Regex("\\p{M}"), "")
        .lowercase()
