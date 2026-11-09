# Weather Mate — Missão 01: O primeiro ecrã

## A tua missão

Constrói o primeiro ecrã da aplicação Weather Mate em Kotlin e Jetpack Compose. Nesta missão, **não precisas de chamar uma API**. Os dados são fornecidos localmente para poderes concentrar-te na interface.

## O que deve aparecer

- Nome da aplicação: Weather Mate.
- Cidade atual: Lisboa.
- Temperatura atual: 22°.
- Condição: Parcialmente nublado.
- Temperatura máxima e mínima: 24° / 17°.
- Uma lista de previsão para os próximos dias.

## Requisitos

1. Usa Jetpack Compose e Material 3.
2. Divide a interface em componentes reutilizáveis.
3. Usa `LazyColumn` para a previsão.
4. Não coloques toda a interface numa única função composable.
5. Mantém os dados fora dos composables visuais.
6. A interface deve adaptar-se a ecrãs de telemóvel comuns.
7. Não é necessário implementar pesquisa, navegação ou chamadas HTTP nesta missão.

## Componentes sugeridos

- `WeatherScreen`
- `WeatherHeader`
- `CurrentWeatherCard`
- `DailyForecastItem`

## Critérios de aceitação

- [ ] A aplicação compila e abre diretamente no ecrã meteorológico.
- [ ] Cidade, temperatura, condição, máxima e mínima estão visíveis.
- [ ] A previsão apresenta pelo menos 4 dias.
- [ ] A lista é composta por elementos reutilizáveis.
- [ ] Os dados estão representados por um modelo Kotlin.
