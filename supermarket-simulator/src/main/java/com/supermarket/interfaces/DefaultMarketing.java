package com.supermarket.interfaces;

import com.supermarket.models.Dia;

public class DefaultMarketing extends MarketingStrategy {
    @Override
    public void applyStrategyType(){
        Dia.getInstanceDia().setClienteMaxDia(40);
    }
}
