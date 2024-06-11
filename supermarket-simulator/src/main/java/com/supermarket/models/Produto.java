package com.supermarket.models;

import javafx.beans.property.*;
import java.text.DecimalFormat;
import java.util.Random;

public class Produto {
    private final StringProperty nome;
    private final DoubleProperty custo;
    private final IntegerProperty quantidade;
    private final IntegerProperty quantidadeAleatoria;
    private final DoubleProperty precoVenda;
    private final DoubleProperty precoSugerido;
    // Esse atributo irá adicionar complexidade no jogo, uma vez que produtos
    // com inflacaoMediaProduto negativa irão dar prejuízo.
    private Double inflacaoMediaProduto;

    private static final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    /**
     * Nesse construtor temos que nos atentar ao fato de que a inflaçãoMediaProduto
     * recebe como entrada um valor 1.5, indicando 1.5%. Desse modo, basta dividirmos
     * dentro do atributo por 100.
     * 
     * @param nome
     * @param custo
     * @param inflacaoMediaProduto
     */
    public Produto(String nome, double custo, double inflacaoMediaProduto) {
        this.nome = new SimpleStringProperty(nome);
        this.custo = new SimpleDoubleProperty(custo);
        this.quantidade = new SimpleIntegerProperty(0);
        this.quantidadeAleatoria = new SimpleIntegerProperty(0);
        this.precoVenda = new SimpleDoubleProperty(custo);
        this.inflacaoMediaProduto = inflacaoMediaProduto / 100;
        this.precoSugerido = new SimpleDoubleProperty(custo * (1 + this.inflacaoMediaProduto));
    }

    public StringProperty getNomeProperty() {
        return nome;
    }

    public DoubleProperty getCustoProperty() {
        return custo;
    }

    public IntegerProperty getQuantidadeProperty() {
        return quantidade;
    }

    public IntegerProperty getQuantidadeAleatoriaProperty() {
        return quantidadeAleatoria;
    }

    public DoubleProperty getPrecoVendaProperty() {
        return precoVenda;
    }

    public DoubleProperty getPrecoSugeridoProperty() {
        return precoSugerido;
    }

    public String getNome() {
        return nome.get();
    }

    public double getCusto() {
        return custo.get();
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public int getQuantidadeAleatoria() {
        return quantidadeAleatoria.get();
    }

    public double getPrecoVenda() {
        return precoVenda.get();
    }

    public double getPrecoSugerido() {
        return precoSugerido.get();
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public void setQuantidadeAleatoria(int quantidadeAleatoria) {
        this.quantidadeAleatoria.set(quantidadeAleatoria);
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda.set(precoVenda);
    }

    public void setPrecoSugerido(double precoSugerido) {
        this.precoSugerido.set(precoSugerido);
    }

    /*
     * 
     * Controles de inflação >>>>>
     * 
     */

    public void setInflacaoTotal(Double inflacaoDia) {
        setPrecoSugerido(getCusto() * (inflacaoDia + this.inflacaoMediaProduto));
    }


    public void sorteiaInflacaoMediaProduto(){
        Random random = new Random();
        // Média Gaussiana: this.inflacaoMediaProduto
        // Desvio: 1
        Double inflacaoProduto = random.nextGaussian() + this.inflacaoMediaProduto;
        // Ajustando para valor decimal
        this.inflacaoMediaProduto = inflacaoProduto / 100;
    }

    // Método para retornar o custo formatado com duas casas decimais
    public StringProperty custoFormattedProperty() {
        String formattedValue = decimalFormat.format(getCusto());
        return new SimpleStringProperty(formattedValue);
    }

    // Método para retornar o preço de venda formatado com duas casas decimais
    public StringProperty precoVendaFormattedProperty() {
        String formattedValue = decimalFormat.format(getPrecoVenda());
        return new SimpleStringProperty(formattedValue);
    }

    // Método para retornar o preço sugerido formatado com duas casas decimais
    public StringProperty precoSugeridoFormattedProperty() {
        String formattedValue = decimalFormat.format(getPrecoSugerido());
        return new SimpleStringProperty(formattedValue);
    }
}
