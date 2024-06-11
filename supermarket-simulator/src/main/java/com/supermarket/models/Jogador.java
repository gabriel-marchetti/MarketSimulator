package com.supermarket.models;

public class Jogador {

    private String nome;
    private double saldo;
    private Estoque estoque;

    public Jogador(String nome, double saldo) {
        this.nome = nome;
        this.saldo = saldo;
        this.estoque = new Estoque();
    }

    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Estoque getEstoque() {
        return estoque;
    }
    
}
