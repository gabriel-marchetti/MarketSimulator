package com.supermarket.controllers;

import com.supermarket.models.Estoque;
import com.supermarket.models.Produto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PrecosController implements Initializable {

    @FXML
    private Label saldo;

    @FXML
    private TreeTableView<Produto> tablePrecos;

    @FXML
    private TreeTableColumn<Produto, String> colunaProduto;

    @FXML
    private TreeTableColumn<Produto, String> colunaCusto;

    @FXML
    private TreeTableColumn<Produto, String> colunaPrecoSugerido;

    @FXML
    private TreeTableColumn<Produto, String> colunaPrecoAtual;

    @FXML
    private TreeTableColumn<Produto, Number> colunaPrecoNovo;

    Estoque estoque = Estoque.getInstance();

    TreeItem<Produto> root = new TreeItem<>(new Produto(null, 0, 0, 0));

    // Mapa para armazenar temporariamente os valores inseridos pelo usuário na
    // colunaPrecoNovo
    private Map<Produto, Double> valoresTemporarios = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Produto produto : estoque.getProdutos()) {
            root.getChildren().add(new TreeItem<>(produto));
        }

        colunaProduto.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, String> param) -> param.getValue().getValue().getNomeProperty());

        colunaCusto.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, String> param) -> param.getValue().getValue().custoFormattedProperty());

        colunaPrecoSugerido.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, String> param) -> param.getValue().getValue().precoSugeridoFormattedProperty());

        colunaPrecoAtual.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<Produto, String> param) -> param.getValue().getValue().precoVendaFormattedProperty());

        // Adicionando a fábrica de células com TextField à colunaPrecoNovo
        colunaPrecoNovo.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                if (object == null) {
                    return "";
                }
                // Formatar o número com no máximo duas casas decimais
                return String.format("%.2f", object.doubleValue());
            }

            @Override
            public Number fromString(String string) {
                try {
                    if (string.isEmpty()) {
                        return null;
                    }
                    return Double.parseDouble(string);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }));

        // Definindo a célula de edição para a colunaPrecoNovo
        colunaPrecoNovo.setOnEditCommit(event -> {
            TreeItem<Produto> item = event.getTreeTableView().getTreeItem(event.getTreeTablePosition().getRow());
            Produto produto = item.getValue();
            produto.setPrecoVenda(event.getNewValue().doubleValue());
        });

        // Habilitando edição na colunaPrecoNovo
        colunaPrecoNovo.setEditable(true);
        tablePrecos.setEditable(true);

        // Adicionando um listener para capturar a edição na colunaPrecoNovo
        colunaPrecoNovo.setOnEditCommit(this::editandoPrecoNovo);

        saldo.setText("Saldo: R$ " + String.format("%.2f", estoque.getSaldo()));

        tablePrecos.setRoot(root);
        tablePrecos.setShowRoot(false);
    }

    private void editandoPrecoNovo(TreeTableColumn.CellEditEvent<Produto, Number> event) {
        Produto produto = event.getRowValue().getValue();
        valoresTemporarios.put(produto, event.getNewValue().doubleValue());
    }

    @FXML
    public void voltar(ActionEvent event) throws Exception {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        irMenu(stage);
    }

    @FXML
    public void concluir(ActionEvent event) throws Exception {
        // Aplicar os valores temporários ao estoque e atualizar a interface
        valoresTemporarios.forEach(Produto::setPrecoVenda);
        valoresTemporarios.clear(); // Limpar os valores temporários

        // Mudar para a próxima cena
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        irMenu(stage);
    }

    private void irMenu(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/supermarket/fxml/Menu.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
    }
}
