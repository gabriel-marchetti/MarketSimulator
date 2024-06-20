package com.supermarket.models;

import java.util.List;
import java.util.Random;

public class Dia {
    private static Dia instancia = null;
    private Double inflacao;
    private Integer diasJogados;
    private Integer diasNegativos;
    private Integer numeroClientesDia;
    private Integer numeroMaxClientesDia;

    private Dia(){
        diasJogados = 0;
        diasNegativos = 0;
        numeroClientesDia = 20;
        numeroMaxClientesDia = 40;
        inflacao = sorteiaInflacao();
    }

    public static Dia getInstanceDia(){
        if( instancia == null ){
            instancia = new Dia();
        }
        return instancia;
    }

    public void resetDia(){
        this.diasJogados = 0;
        this.diasNegativos = 0;
        this.numeroClientesDia = 20;
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
        this.diasJogados = this.diasJogados + 1;
        this.inflacao = sorteiaInflacao();
        this.numeroClientesDia = sorteiaNumeroClientes();
    }

    /*
     * Irá sortear o número de clientes do dia baseado nos preços. Aqui temos
     * uma base de clientes do dia sendo 20, contudo dado que os preços são bons
     * os consumidores irão à loja. Entretanto temos um limite máximo de clientes
     * na loja.
     */
    public Integer sorteiaNumeroClientes(){
        List<Produto> produtos = Estoque.getInstance().getProdutos();
        Double taxaPorcentual = 1.0;
        for( Produto produto : produtos ){
            taxaPorcentual *= produto.getPrecoSugerido() / produto.getPrecoVenda();
        }

        Integer numClientes = (int) Math.round(taxaPorcentual * 20.0);
        if( numClientes > numeroMaxClientesDia ){
            numClientes = numeroMaxClientesDia;
        }

        return numClientes;
    }

    /*
     * Setters e getters ---
     */

    public Integer getDiasNegativos(){
        return this.diasNegativos;
    }

    public void setDiasNegativos( Integer diasNegativos ){
        this.diasNegativos = diasNegativos;
    }

    public void resetDiasNegativos( ){
        this.diasNegativos = 0;
    }

    public void increaseDiasNegativos(){
        this.diasNegativos++;
    }

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

    public Integer getClientesDia( ){
        return this.numeroClientesDia;
    }

    public void setClienteMaxDia( Integer numeroMaxClientesDia ){
        this.numeroMaxClientesDia = numeroMaxClientesDia;
    }

    public Integer getClienteMaxDia(){
        return this.numeroMaxClientesDia;
    }
}
