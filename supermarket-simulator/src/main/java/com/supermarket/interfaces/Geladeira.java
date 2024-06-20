package com.supermarket.interfaces;

import com.supermarket.models.EquipamentosLoja;
import com.supermarket.models.Estoque;

public class Geladeira extends Equipamento {
    Double gastoAluguelGeladeira = 20.00;

    @Override
    public Double gastoEquipamento(){
        return gastoAluguelGeladeira;
    }
    
    @Override
    public void melhoria(){
        /*
         * Compra geladeira por 100.00 reais. 
         */
        Estoque.getInstance().pagar(100.0);
        // Recebe a geladeira.
    }
}
