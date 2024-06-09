package com.supermarket.controllers;

import com.supermarket.models.Estoque;
import com.supermarket.models.Produto;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.beans.property.SimpleDoubleProperty;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CompraController implements Initializable {

    private Double totalCompra = 0.0;

    @FXML
    private Label total;

    @FXML
    private Label saldo;

    private Map<Produto, ComboBox<String>> comboBoxes;
    private Map<Produto, Integer> quantidadesSelecionadas;

    @FXML
    private TreeTableView<Produto> tableView;

    @FXML
    private TreeTableColumn<Produto, String> colunaProduto;

    @FXML
    private TreeTableColumn<Produto, String> colunaCusto;

    @FXML
    private TreeTableColumn<Produto, Number> colunaQntdAtual;

    @FXML
    private TreeTableColumn<Produto, Integer> colunaQntdCompra;

    @FXML
    private TreeTableColumn<Produto, Number> colunaTotal;

    Estoque estoque = Estoque.getInstance();

    TreeItem<Produto> root = new TreeItem<>(new Produto(null, 0, 0, 0));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Produto produto : estoque.getProdutos()) {
            root.getChildren().add(new TreeItem<>(produto));
        }

        colunaProduto.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, String> param) -> param.getValue().getValue().getNomeProperty());
        colunaCusto.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, String> param) -> param.getValue().getValue().custoFormattedProperty());
        colunaQntdAtual.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, Number> param) -> param.getValue().getValue().getQuantidadeProperty());

        comboBoxes = new HashMap<>();
        quantidadesSelecionadas = new HashMap<>();

        colunaQntdCompra
                .setCellFactory(new Callback<TreeTableColumn<Produto, Integer>, TreeTableCell<Produto, Integer>>() {
                    @Override
                    public TreeTableCell<Produto, Integer> call(TreeTableColumn<Produto, Integer> param) {
                        return new TreeTableCell<Produto, Integer>() {
                            private ComboBox<String> comboBox = new ComboBox<>();

                            {
                                comboBox.setItems(
                                        FXCollections.observableArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8",
                                                "9", "10"));
                                comboBox.setOnAction(event -> {
                                    Produto produto = getTableRow().getItem();
                                    if (produto != null) {
                                        int quantidade = Integer.parseInt(comboBox.getValue());
                                        quantidadesSelecionadas.put(produto, quantidade);
                                        atualizarTotal();
                                    }
                                });
                            }

                            @Override
                            protected void updateItem(Integer item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                } else {
                                    Produto produto = getTableRow().getItem();
                                    if (produto != null) {
                                        comboBox.setValue(
                                                String.valueOf(quantidadesSelecionadas.getOrDefault(produto, 0)));
                                        comboBoxes.put(produto, comboBox);
                                        setGraphic(comboBox);
                                    }
                                }
                            }
                        };
                    }
                });

        colunaTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<Produto, Number> param) -> {
            Produto produto = param.getValue().getValue();
            if (produto != null) {
                int quantidade = quantidadesSelecionadas.getOrDefault(produto, 0);
                double total = quantidade * produto.getCusto();
                return new SimpleDoubleProperty(total);
            }
            return new SimpleDoubleProperty(0);
        });

        colunaTotal.setCellFactory(column -> new TreeTableCell<Produto, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.format("%.2f", item.doubleValue()));
                }
            }
        });

        saldo.setText("Saldo: R$ " + String.format("%.2f", estoque.getSaldo()));
        total.setText("Total: R$ 0.00");

        tableView.setRoot(root);
        tableView.setShowRoot(false);
    }

    @FXML
    public void atualizarTotal() {
        Double saida = calculaTotal();
        total.setText("Total: R$ " + String.format("%.2f", saida));
        tableView.refresh(); // Atualiza a TableView para refletir as mudanças
    }

    private double calculaTotal() {
        totalCompra = 0.0;
        for (Map.Entry<Produto, Integer> entry : quantidadesSelecionadas.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            totalCompra += quantidade * produto.getCusto();
        }
        return totalCompra;
    }

    @FXML
    public void atualizarSaldo() {
        // Atualizando o saldo
        estoque.setSaldo(estoque.getSaldo() - totalCompra);
    }

    @FXML
    public void voltar(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        irMenu(stage);
    }

    @FXML
    public void concluir(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        // Implementar lógica para concluir a compra

        // Atualizando a quantidade dos produtos
        for (Map.Entry<Produto, Integer> entry : quantidadesSelecionadas.entrySet()) {
            Produto produto = entry.getKey();
            int quantidade = entry.getValue();
            estoque.setQuantidadeProdutos(estoque.getQuantidadeProdutos() + quantidade);
            produto.setQuantidade(produto.getQuantidade() + quantidade);
        }
        atualizarSaldo();
        irMenu(stage);

    }

    private void irMenu(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/Menu.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }
}
