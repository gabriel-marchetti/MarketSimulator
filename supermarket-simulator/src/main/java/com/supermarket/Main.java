package com.supermarket;


import com.supermarket.leitura.LerProdutos;
import com.supermarket.models.Estoque;

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
        estoque.setCapacidadeMaxima(20);
        
        LerProdutos leitura = new LerProdutos();
        leitura.LerArquivo(estoque, "produtos.xml");

        
        launch(args);
    }
}