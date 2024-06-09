package com.supermarket;

import java.util.List;

import com.supermarket.models.Estoque;
import com.supermarket.models.Produto;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Supermarket Simulator");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/TelaInicial.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Estoque estoque = Estoque.getInstance();
        estoque.setCapacidadeMaxima(1000);
        
        Produto prod1 = new Produto("Arroz", 10.0, 15.0, 1.5);
        Produto prod2 = new Produto("Feijão", 5.0, 8.0, 1.7);
        Produto prod3 = new Produto("Macarrão", 3.0, 5.0, 1.9);
        Produto prod4 = new Produto("Açúcar", 4.0, 6.0, 2.3);
        List<Produto> produtos = List.of(prod1, prod2, prod3, prod4);
        estoque.setProdutos(produtos);

        
        launch(args);
    }
}