package com.supermarket.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.supermarket.models.EquipamentosLoja;
import com.supermarket.models.Estoque;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MelhoriasController implements Initializable {
    @FXML
    private Label saldoAtual;
    @FXML
    private Label limiteEstoque;
    @FXML
    private Label estoqueAtual;

    @FXML
    private void handleVoltar(ActionEvent event){
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        irMenu(stage);
    }

    private void irMenu(Stage stage) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/Menu.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        }
        catch( IOException e ){
            System.out.println("Erro ao voltar para o menu");
            e.getStackTrace();
        }
    }

    @FXML
    private void handleMelhorarEstoque(ActionEvent event){
        Estoque.getInstance().improve();
        Double saldoAtual = Estoque.getInstance().getSaldo(),
               precoMelhoria = 100.0;
        Estoque.getInstance().setSaldo(saldoAtual - precoMelhoria );
        atualizaLabels();
    }

    @FXML
    private void handleComprarGeladeira(ActionEvent event){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Parabéns pela aquisição.");
        alert.setContentText("Você comprou uma geladeira");
        alert.showAndWait();
        EquipamentosLoja.compraGeladeira();
        atualizaLabels();
    }

    public void initialize(URL url, ResourceBundle rb){
        atualizaLabels();
    }

    public void atualizaLabels(){
        String saldoTxt = new String();
        saldoTxt = "R$ " + Estoque.getInstance().getSaldo().toString();
        saldoAtual.setText(saldoTxt);
        estoqueAtual.setText(Estoque.getInstance().getQuantidadeProdutos().toString());
        limiteEstoque.setText(Estoque.getInstance().getCapacidadeMaxima().toString());
    }
}
