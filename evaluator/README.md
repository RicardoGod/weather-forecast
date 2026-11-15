# Avaliador visual

Compara screenshot de referência e screenshot do candidato através de SSIM global e por regiões.

## Uso
```bash
python -m pip install -r requirements.txt
python compare.py --reference reference/weather-home.png --candidate candidate.png --config config.json
```

## Atenção
- Criar `reference/weather-home.png` a partir da solução de referência, no emulador fixado.
- Capturar o candidato com o mesmo tamanho, densidade, font scale, locale e dados determinísticos.
- A comparação redimensiona a imagem candidata para o tamanho da referência; isto não substitui a normalização correta do ambiente.
- O SSIM é apenas um sinal de avaliação. Não deve ser usado sozinho como decisão de aprovação.
- Não comparar screenshots de dados meteorológicos reais com dados de referência variáveis. A Missão 01 usa sempre dados locais.
