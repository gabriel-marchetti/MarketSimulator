package com.supermarket.leitura;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.supermarket.models.Estoque;
import com.supermarket.models.Produto;

public class LerProdutos {
    public void LerArquivo(Estoque estoque, String path) {
        List<Produto> produtos = new ArrayList<>();

        try {
            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Produto");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element produtoElement = (Element) nodeList.item(i);

                String nome = produtoElement.getElementsByTagName("nome").item(0).getTextContent();
                double custo = Double
                        .parseDouble(produtoElement.getElementsByTagName("custo").item(0).getTextContent());
                double inflacaoMediaProduto = Double.parseDouble(
                        produtoElement.getElementsByTagName("inflacaoMediaProduto").item(0).getTextContent());

                Produto produto = new Produto(nome, custo, inflacaoMediaProduto);
                produtos.add(produto);
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        estoque.setProdutos(produtos);
    }
}
