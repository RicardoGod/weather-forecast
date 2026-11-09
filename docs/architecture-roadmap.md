# Evolução técnica proposta

## Missões 01–02: UI primeiro
Usar dados locais e um fake repository. O candidato aprende a representar o estado e a reagir a inputs sem ainda depurar problemas de rede.

## Missão 03: API
Fluxo recomendado:
1. Geocoding por nome de cidade.
2. Mostrar resultados de pesquisa.
3. Escolher um resultado e obter latitude/longitude.
4. Pedir forecast.
5. Mapear DTOs para modelos usados pela aplicação.

## Missões 04–05: estados e arquitetura
Introduzir `WeatherUiState` (por exemplo Loading, Success, Error) e ViewModel. Evitar abstrações excessivas antes de o candidato compreender o problema.

## Missão 06: Room
Guardar favoritos localmente. Não é necessário guardar toda a previsão meteorológica na primeira versão.

## Missão 07: testes
Usar fake data sources e dispatchers controlados nos testes. UI tests verificam comportamento e semantics; screenshots verificam aspeto visual.
