package com.supermarket.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.channels.SelectableChannel;
import java.util.List;
import java.util.ResourceBundle;

import com.supermarket.interfaces.DefaultMarketing;
import com.supermarket.interfaces.MarketingStrategy;
import com.supermarket.interfaces.MoreMarketing;
import com.supermarket.models.Dia;
import com.supermarket.models.EquipamentosLoja;
import com.supermarket.models.EstadoJogo;
import com.supermarket.models.Estoque;
import com.supermarket.models.Inspetor;
import com.supermarket.models.Produto;
import com.supermarket.models.SelectedStrategy;

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
    @FXML
    private Label saldoLabel;
    @FXML
    private Label diasJogadosLabel;
    @FXML
    private Label diasNegativos;
    @FXML
    private Button passarDiaButton;
    @FXML
    private Label clientesDia;
    @FXML
    private Label estrategiaSelecionadaLabel;
    @FXML
    private Label maxClientesLabel;
    @FXML
    private Button estrategiaButton;

    SelectedStrategy estrategiaSelecionada;

    private Estoque estoque = Estoque.getInstance();
    private Dia dia = Dia.getInstanceDia();

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

    @FXML
    public void handleMelhoriasButton(ActionEvent event){
        try{
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/supermarket/fxml/Melhorias.fxml"));
            stage.setScene(new Scene(root));
        }
        catch( IOException e ){
            System.out.println("Problema abrindo o painel de melhorias");
            e.getStackTrace();
        }
    }

    @FXML
    public void handleEstrategiaSelecionada(ActionEvent event){
        // System.out.println(estrategiaSelecionada.getStrategy().toString());
        if( estrategiaSelecionada.getStrategy() instanceof MoreMarketing ){
            estrategiaButton.setText("Aumentar Marketing");
            DefaultMarketing defaultStrategy = new DefaultMarketing();
            estrategiaSelecionada.setStrategy(defaultStrategy);
        }
        else if( estrategiaSelecionada.getStrategy() instanceof DefaultMarketing ){
            estrategiaButton.setText("Parar com marketing");
            MoreMarketing moreStrategy = new MoreMarketing();
            estrategiaSelecionada.setStrategy(moreStrategy);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Mudança de estratégia");
            alert.setHeaderText("Você terá que pagar diariamente devido à mudança");
        }

        estrategiaSelecionada.getStrategy().applyStrategyType();
        atualizaLabelStatus();
    }

    @FXML
    public void passarDiaAction( ActionEvent event ) throws Exception{
        EstadoJogo estadoJogo;
        Dia.getInstanceDia().passaDia();
        atualizaPrecosInflacao();
        Integer dia = Dia.getInstanceDia().getDiasJogados();
        /**
         * Aqui temos a função que verifica o dia do pagamento. No caso queremos
         * dias divisíveis por 7.
         */
        if( dia % 7 == 0 && dia != 0 ){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("DIA DO PAGAMENTO.");
            alert.setHeaderText(null);
            alert.setContentText("Seu Barriga chegou e quer que você pague.");
            alert.showAndWait();
            pagarAluguel();
        }
        pagaBonusEstrategia();
         /**
         * Aqui verificaremos se neste dia haverá inspeção de Eric Jackin
         */
        if (Inspetor.temInspecao()) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("INSPETOR CHEGOU!");
            alert.setHeaderText(null);
            alert.setContentText("ÉRICK JACQUIN: BAMOS BER LAS FREEZERS");
            alert.showAndWait();
            if (EquipamentosLoja.verificaGeladeira().equals(false)) {
                alert.setTitle("DONDE ESTÁ LA FREEZER?");
                alert.setHeaderText(null);
                alert.setContentText("ÉS LA VERGOIN DE LA PROFISSIÓN!");
                alert.showAndWait();
                Estoque.getInstance().pagar(500.0);
            }
        }


        /* 
        * TO-DO: Precisamos adicionas o método que reseta o jogo caso o 
        * jogador perca.
        */
        estadoJogo = verificaPerdeu();
        if( estadoJogo == EstadoJogo.PERDEU ){
            String path = "Record.txt";
            String record = "SUA PONTUACAO: " + Dia.getInstanceDia().getDiasJogados().toString();
            adicionarRegistro(path, record);
            resetJogo();
        }

        /*
         * Sorteio uma nova variação de inflação específica dos produtos.
         */
        List<Produto> produtos = Estoque.getInstance().getProdutos();
        for( Produto produto : produtos ){
            produto.sorteiaInflacaoMediaProduto();
        }

        atualizaLabelStatus();
    }

    @Override
    public void initialize(URL url, ResourceBundle resources){
        if( estrategiaSelecionada == null ){
            estrategiaSelecionada = new SelectedStrategy();
            MarketingStrategy tipoEstrategia = new DefaultMarketing();
            estrategiaSelecionada.setStrategy(tipoEstrategia);
        }
        atualizaLabelStatus();
    }

    private void atualizaLabelStatus(){
        diasJogadosLabel.setText(dia.getDiasJogados().toString());
        String valorString = String.format("%.2f", estoque.getSaldo());
        saldoLabel.setText("R$ " + valorString);
        diasNegativos.setText(dia.getDiasNegativos().toString());
        clientesDia.setText(Dia.getInstanceDia().getClientesDia().toString());
        maxClientesLabel.setText(Dia.getInstanceDia().getClienteMaxDia().toString());
    }

    /**
     * Esse método irá adicionar dificuldade dentro do jogo, de modo que
     * todo dia que for divisível por 7 o jogador deve pagar o aluguel do
     * estabelecimento. 
     * 
     * Para aumento da dificuldade, podemos fazer uma taxa base e uma taxa 
     * percentual do estabelecimento. Na medida que o jogo progride, é esperado
     * que ele fique mais dificil. Por conta disso iremos adicionar uma função
     * logaritmica e uma função que tira percentualmente valor da loja.
     */
    private void pagarAluguel(){
        Integer diasJogados = Dia.getInstanceDia().getDiasJogados();
        Double saldoLoja = Estoque.getInstance().getSaldo();
        
        Double aluguel = Math.log(diasJogados) * 100 + 0.2 * saldoLoja;

        // Checa equipamentos.
        if( EquipamentosLoja.verificaGeladeira() ){
            aluguel += EquipamentosLoja.geladeiraAluguel();
        }

        Estoque.getInstance().setSaldo( saldoLoja - aluguel );
    }


    public void pagaBonusEstrategia(){
        if( estrategiaSelecionada.getStrategy() instanceof MoreMarketing ){
            Estoque.getInstance().pagar(20.0);
        }
    }

    /**
     * Esse método retorna se o jogador perdeu o jogo dada uma entrada de Dia.
     * @return
     */
    private EstadoJogo verificaPerdeu(){
        if( Estoque.getInstance().getSaldo() < 0 )
            Dia.getInstanceDia().increaseDiasNegativos();

        if( Dia.getInstanceDia().getDiasNegativos() >= 4 )
            return EstadoJogo.PERDEU;
        else if( Estoque.getInstance().getSaldo() <= -1000.0 )
            return EstadoJogo.PERDEU;
        else
            return EstadoJogo.CONTINUA;
    }

    /**
     * Esse método tem como finalidade ajudar na atualização do preço. Veja que
     * queremos atualizar os preços na medida que atualizamos os dias.
     */
    private void atualizaPrecosInflacao(){
        Double inflacao = Dia.getInstanceDia().getInflacao();
        Estoque estoque = Estoque.getInstance();

        for( Produto produto : estoque.getProdutos() ){
            produto.setInflacaoTotal( 1.0 + inflacao);
        }
    }

    /**
     * Função resetJogo para caso o jogador cumpra a condição de perda.
     */
    private void resetJogo(){
        Dia.getInstanceDia().resetDia();
        Estoque.getInstance().resetEstoque();
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("PERDEU");
        alert.setHeaderText(null);
        alert.setContentText("Seu supermercado faliu, recomece o jogo.");
        alert.showAndWait();
    }

    public static void adicionarRegistro(String path, String text){
        // Verifica se o arquivo já existe
        File arquivo = new File(path);

        // Se o arquivo não existir, cria um novo arquivo
        if (!arquivo.exists()) {
            try{
                arquivo.createNewFile();
                System.out.println("Arquivo criado: " + path);    
            }
            catch( Exception e ){
                e.printStackTrace();
            }
        }

        try (FileWriter fw = new FileWriter(path, true); // true para append
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            out.println(text); // Escreve a linha no arquivo
            System.out.println("Linha adicionada com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro ao adicionar linha no arquivo: " + e.getMessage());
        }
    }

}
