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
        // Gera um número inteiro entre 0 e 30
        int chance = random.nextInt(4);
        // Se o número gerado for 7, haverá inspeção!
        return chance == 3;
    }
}
