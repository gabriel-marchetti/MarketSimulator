package com.supermarket.controllers;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import com.supermarket.models.Estoque;
import com.supermarket.models.Produto;


import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class VendaController implements Initializable {

    @FXML
    private ComboBox<Integer> nota05;

    @FXML
    private ComboBox<Integer> nota1;

    @FXML
    private ComboBox<Integer> nota10;

    @FXML
    private ComboBox<Integer> nota20;

    @FXML
    private ComboBox<Integer> nota5;

    @FXML
    private Label pago;

    @FXML
    private Label saldo;

    @FXML
    private Label total;

    @FXML
    private Label troco;

    @FXML
    private Label trocoAtual;

    @FXML
    private TreeTableView<Produto> tableVenda;

    @FXML
    private TreeTableColumn<Produto, String> colunaProduto;

    @FXML
    private TreeTableColumn<Produto, Number> colunaPrecoVenda;

    @FXML
    private TreeTableColumn<Produto, Number> colunaQuantidade;

    private Double totalVenda;
    private Double valorPago;
    private Double valorTroco;
    private Double valorTrocoAtual;

    Estoque estoque = Estoque.getInstance();
    TreeItem<Produto> root = new TreeItem<>(new Produto(null, 0, 0, 0));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Adicionando valores de 0 a 4 nas comboboxes
        for (int i = 0; i < 5; i++){
            nota05.getItems().add(i);
            nota1.getItems().add(i);
            nota10.getItems().add(i);
            nota20.getItems().add(i);
            nota5.getItems().add(i);
        };

        // Setando os valores iniciais das comboboxes
        nota05.setValue(0);
        nota1.setValue(0);
        nota10.setValue(0);
        nota20.setValue(0);
        nota5.setValue(0);
        

        // Adicionando listener para que as comboboxes sejam reativas e atualizem o troco atual
        ChangeListener<Integer> listener = (observable, oldValue, newValue) -> updateTrocoAtual();
        nota05.valueProperty().addListener(listener);
        nota1.valueProperty().addListener(listener);
        nota10.valueProperty().addListener(listener);
        nota20.valueProperty().addListener(listener);
        nota5.valueProperty().addListener(listener);

        // inicializando a tabela de vendas
        estoque.venderProduto();
        for (Produto produto : estoque.getProdutos()) {
            root.getChildren().add(new TreeItem<>(produto));
        }

        colunaProduto.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, String> param) -> param.getValue().getValue().getNomeProperty());
        colunaPrecoVenda.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, Number> param) -> param.getValue().getValue().getPrecoVendaProperty());
        colunaQuantidade.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, Number> param) -> param.getValue().getValue().getQuantidadeAleatoriaProperty());

        //calculando os parametros e atualizando as labels
        calculaTotalVenda();
        calculaValorPago();
        valorTroco = valorPago - totalVenda;
        calculaTrocoAtual();

        saldo.setText("Saldo: R$ " + String.format("%.2f", estoque.getSaldo()));
        total.setText("Total: R$ " + String.format("%.2f", totalVenda));
        pago.setText("Pago: R$ " + String.format("%.2f", valorPago));
        troco.setText("Troco: R$ " + String.format("%.2f", valorTroco));
        trocoAtual.setText("R$ " + String.format("%.2f", valorTrocoAtual));

        tableVenda.setRoot(root);
        tableVenda.setShowRoot(false);

        
    }

    private void calculaTotalVenda() {
        totalVenda = 0.0;
        for (TreeItem<Produto> item : root.getChildren()) {
            Produto produto = item.getValue();
            totalVenda += produto.getPrecoVenda() * produto.getQuantidadeAleatoria();
        }
    }

    private void calculaValorPago() {
        Random random = new Random();
        

        valorPago = random.nextDouble() * 30 + totalVenda;
    }

    private void calculaTrocoAtual() {
        valorTrocoAtual = nota05.getValue() * 0.5 + nota1.getValue() * 1 + nota10.getValue() * 10 + nota20.getValue() * 20 + nota5.getValue() * 5;
    }

    private void updateTrocoAtual() {
        calculaTrocoAtual();
        trocoAtual.setText("R$ " + String.format("%.2f", valorTrocoAtual));
    }

    @FXML
    public void atualizarSaldo() {
        // Atualizando o saldo
        estoque.setSaldo(estoque.getSaldo() + valorPago - valorTrocoAtual);
    }

    @FXML
    public void voltar(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        irMenu(stage);
    }

    @FXML
    public void concluir(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        if( valorTrocoAtual >= valorTroco ){
            for (Produto produto : estoque.getProdutos()) {
                produto.setQuantidade(produto.getQuantidade() - produto.getQuantidadeAleatoria());
                estoque.setQuantidadeProdutos(estoque.getQuantidadeProdutos() - produto.getQuantidadeAleatoria());
            }
            atualizarSaldo();
            irMenu(stage);
        }
    }

    private void irMenu(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/Menu.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }
}
