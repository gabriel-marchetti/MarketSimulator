package com.supermarket.models;

import java.util.Random;

public class Inspetor {
    /**
     * Aqui quero criar uma função que verifica se haverá inspeção dentro do
     * estabelecimento.
     * @return true se houver inspeção, false caso contrário
     */
    public static Boolean temInspecao() {
        Random random = new Random();
        /*
         * Aqui queremos gerar um número aleatório de 0 até 30, pois queremos
         * implementar uma mecânica de uma visita sanitária por mês. Assumindo
         * por questão de adicionar simplicidade no jogo que todos os meses
         * tem 31 dias.
         */
        int chance = random.nextInt(31);
        // Se o número gerado for 7, haverá inspeção!
        return chance == 7;
    }
}
