package com.supermarket.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.supermarket.models.Dia;
import com.supermarket.models.Estoque;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class MenuController implements Initializable{
    private Dia dia = new Dia();

    @FXML
    private Label saldoLabel;
    @FXML
    private Label diasJogadosLabel;

    private Estoque estoque = Estoque.getInstance();

    @FXML
    public void handleVenderButton(ActionEvent event) throws Exception {
    // Verifique se o estoque está vazio
    if (estoque.estoqueEstaVazio()) {
        // Exiba uma mensagem de erro
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro de Estoque");
        alert.setHeaderText(null);
        alert.setContentText("O estoque está vazio. Não é possível realizar a venda.");
        alert.showAndWait();
    } else {
        // Prossiga com a venda
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/Venda.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }
}

    @FXML
    public void handleReporButton(ActionEvent event) throws Exception {
        if (estoque.estoqueEstaCheio()) {
            // Exiba uma mensagem de erro
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erro de Estoque");
            alert.setHeaderText(null);
            alert.setContentText("O estoque está cheio. Não é possível repor produtos.");
            alert.showAndWait();
        } else {
            // Prossiga com a reposição
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/Compra.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        }
    }

    @FXML
    public void handleAlterarPrecosButton(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/supermarket/fxml/Precos.fxml"));
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resources){
        diasJogadosLabel.setText(dia.getDiasJogados().toString());
        saldoLabel.setText("R$ ");
    }
}
