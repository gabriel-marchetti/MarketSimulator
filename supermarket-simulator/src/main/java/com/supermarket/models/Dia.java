package com.supermarket.models;

import java.util.Random;

public class Dia {
    private Double inflacao;
    private Integer diasJogados;

    public Dia(){
        diasJogados = 0;
        inflacao = sorteiaInflacao();
    }

    /**
     * Esse método irá retornar a nossa taxa de inflação do dia. Veja que esse
     * método tornará o jogo estocástico e por conta disso haverá maior 
     * dificuldade para o jogo.
     * 
     * Por uma questão de jogabilidade iremos introduzir uma inflação diária média
     * de 5% e desvio-padrão de 10%. Desse modo, fazendo a conta da distribuição
     * acumulada, veremos que 30,85% dos dias de jogo terão taxa de inflação negativa
     * desse modo haverá redução de preços também.
     * 
     * @return inflacaoDiaria
     */
    private Double sorteiaInflacao(){
        Random random = new Random();
        // 10 por conta do desvio-padrão
        // 5 por conta da média, veja que nextGaussian() define uma normal-padrão.
        Double inflacao = random.nextGaussian()*10 + 5;

        return inflacao / 100.0;
    }

    /**
     * Esse método será utilizado para contabilizar o número de dias Jogados. 
     * Além disso irá sortear uma nova inflação diária. 
     */
    public void passaDia(){
        this.diasJogados++;
        this.inflacao = sorteiaInflacao();
    }

    /*
     * Setters e getters ---
     */
    public Double getInflacao() {
        return inflacao;
    }

    public void setInflacao(Double inflacao) {
        this.inflacao = inflacao;
    }

    public Integer getDiasJogados() {
        return diasJogados;
    }

    public void setDiasJogados(Integer diasJogados) {
        this.diasJogados = diasJogados;
    }
}
