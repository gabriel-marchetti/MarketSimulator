package com.supermarket.interfaces;

public class Geladeira extends Equipamento {
    Double gastoBaseGeladeira = 100.00;

    @Override
    public Double gastoEquipamento(){
        return gastoBaseGeladeira;
    }
    
    @Override
    public void melhoria(){
        
    }
}
