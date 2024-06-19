package com.supermarket.models;

import java.util.List;
import java.util.Random;

import com.supermarket.interfaces.Improvable;

public class Estoque implements Improvable {
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

    public void resetEstoque(){
        this.quantidadeProdutos = 0;
        this.saldo = 1000.0;
        resetListaProdutos();
        this.capacidadeMaxima = 20;
    }

    public void improve(){
        this.capacidadeMaxima += 20;
    }

    private void resetListaProdutos(){
        for( Produto produto : Estoque.getInstance().getProdutos() ){
            produto.setQuantidade(0);
        }
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

    public boolean estoqueEstaVazio(){
        return quantidadeProdutos == 0;
    }

    public boolean estoqueEstaCheio(){
        return quantidadeProdutos == capacidadeMaxima;
    }

    public void venderProduto() {
        Random random = new Random();
        for (Produto produto : produtos) {
            double diferencaPreco = produto.getPrecoSugerido() - produto.getPrecoVenda();
            int quantidadeBase = produto.getQuantidade();
            int quantidadeAjustada = (int) (quantidadeBase - diferencaPreco);

            // Se a diferença de preço for negativa, aumenta a quantidade
            if (diferencaPreco < 0) {
                quantidadeAjustada = (int) (quantidadeBase - (diferencaPreco * -1));
            }

            // Garante que a quantidade ajustada esteja entre 0 e a quantidade do produto
            quantidadeAjustada = Math.max(0, Math.min(quantidadeAjustada, quantidadeBase));

            int quantidadeAleatoria = random.nextInt(quantidadeAjustada + 1);
            produto.setQuantidadeAleatoria(quantidadeAleatoria);
        }
    }

    public void pagar(double divida) {
        this.saldo -= divida;
    }

}