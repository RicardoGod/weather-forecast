# Missão 05 — Organizar com MVVM

## Objetivo
Separar interface, estado e acesso a dados.

## Estrutura sugerida
- `ui/`: composables e componentes.
- `ui/state/`: estado apresentado pela UI.
- `viewmodel/`: coordenação de ações e estado.
- `data/remote/`: serviços e DTOs da API.
- `data/repository/`: abstração dos dados.
- `domain/model/`: modelos usados pela aplicação.

## Critérios
- [ ] Composables não fazem pedidos HTTP.
- [ ] ViewModel expõe estado observável.
- [ ] Repository encapsula as fontes de dados.
- [ ] A UI envia eventos/intents para o ViewModel.
- [ ] A implementação é explicável e não excessivamente abstrata.
