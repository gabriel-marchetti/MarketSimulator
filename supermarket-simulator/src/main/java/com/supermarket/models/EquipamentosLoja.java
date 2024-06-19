package com.supermarket.models;

import com.supermarket.interfaces.Geladeira;

public class EquipamentosLoja {
    private static Geladeira geladeiraLoja;

    public static void compraGeladeira(){
        geladeiraLoja = new Geladeira();     
        geladeiraLoja.melhoria();   
    }

    public static Boolean verificaGeladeira(){
        return !(geladeiraLoja == null);
    }

    public static Double geladeiraAluguel(){
        if( geladeiraLoja != null )
            return geladeiraLoja.gastoEquipamento();
        return 0.0;
    }
}
