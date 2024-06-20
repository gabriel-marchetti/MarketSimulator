package com.supermarket;

import java.io.IOException;
import java.util.List;

import com.supermarket.leitura.LerProdutos;
import com.supermarket.models.Estoque;
import com.supermarket.models.Produto;
import com.supermarket.models.Dia;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Supermarket Simulator");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/TelaInicial.fxml"));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch( IOException loaderException ) {
            System.out.println("Erro no loader.");
            System.out.println("Tentando novamente...");
        } catch( NullPointerException fileException ){
            System.out.println("Não achei TelaInicial.fxml");
        } catch( Exception e ){
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) {
        Estoque estoque = Estoque.getInstance();
        estoque.setCapacidadeMaxima(20);
        
        LerProdutos leitura = new LerProdutos();
        leitura.LerArquivo(estoque, "produtos.xml");

        
        try{
            launch(args);
        }
        catch( Exception e ){
            System.out.println("Falha ao iniciar o programa");
            e.getStackTrace();
        }
    }
}