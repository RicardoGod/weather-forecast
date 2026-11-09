# Missão 03 — Consultar o tempo real

## Objetivo
Substituir os dados meteorológicos fictícios por dados da API Open-Meteo.

## Endpoints
Geocoding:
`https://geocoding-api.open-meteo.com/v1/search?name={cidade}&count=5&language=pt&format=json`

Forecast:
`https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m,relative_humidity_2m,apparent_temperature,is_day,precipitation,weather_code,wind_speed_10m&daily=weather_code,temperature_2m_max,temperature_2m_min&timezone=auto`

## Requisitos
- Pesquisa real de cidade através do endpoint Geocoding.
- Seleção de um resultado com latitude/longitude.
- Pedido de previsão para as coordenadas selecionadas.
- Converter a resposta JSON para modelos Kotlin.
- Mostrar temperatura atual, sensação térmica, vento e previsão diária.
- Não bloquear a Main Thread.

## Notas
- Usa uma biblioteca HTTP/serialização aprovada pelo mentor (por exemplo Retrofit + Kotlin Serialization).
- Não assumes que a resposta tem sempre resultados.
- Trata erros HTTP, falhas de rede e respostas incompletas.
- Não precisas de implementar Room nesta missão.

## Critérios
- [ ] Pesquisa devolve cidades reais.
- [ ] Seleção da cidade obtém a previsão correspondente.
- [ ] Pedidos decorrem fora da Main Thread.
- [ ] JSON é convertido para modelos tipados.
- [ ] Não existem crashes quando a API devolve zero resultados ou falha.
