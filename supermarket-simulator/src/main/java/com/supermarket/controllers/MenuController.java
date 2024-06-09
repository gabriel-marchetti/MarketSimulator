package com.supermarket.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;


public class MenuController {

    @FXML
    public void handleVenderButton(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/Venda.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }

    @FXML
    public void handleReporButton(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/supermarket/fxml/Compra.fxml"));
        stage.setScene(new Scene(root));
    }

    @FXML
    public void handleAlterarPrecosButton(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/supermarket/fxml/Precos.fxml"));
        stage.setScene(new Scene(root));
    }
}
