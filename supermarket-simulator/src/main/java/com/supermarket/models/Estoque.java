package com.supermarket.models;

import java.util.List;
import java.util.Random;

public class Estoque {
    private static Estoque instancia = null;
    private List<Produto> produtos;
    private Integer quantidadeProdutos;
    private Integer capacidadeMaxima;
    private Double saldo;

    /**
     * Construtor privado para o Estoque
     */
    protected Estoque() 
    {
        this.quantidadeProdutos = 0;
        this.saldo = 1000.0;
        this.capacidadeMaxima = Integer.MAX_VALUE;
    }

    /**
     * Verifica se a instancia estoque já foi criada, caso não, cria uma nova
     */
    public static Estoque getInstance(){
        if (instancia == null){
            instancia = new Estoque();
        }
        return instancia;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Integer getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    public void setQuantidadeProdutos(Integer quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    public Integer getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public void setCapacidadeMaxima(Integer capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void venderProduto(){
        //baseado no valor get quantidade, retorna uma quantidade aleatória
        for(Produto produto : produtos){
            Random random = new Random();
            int quantidadeAleatoria = random.nextInt(produto.getQuantidade() + 1);
            produto.setQuantidadeAleatoria(quantidadeAleatoria);
        }
    }

}