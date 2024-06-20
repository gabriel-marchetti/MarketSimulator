package com.supermarket.models;

import com.supermarket.interfaces.MarketingStrategy;

import javafx.beans.binding.BooleanExpression;

public class SelectedStrategy {
    private MarketingStrategy marketingStrategy;

    public void setStrategy( MarketingStrategy marketingStrategy ){
        this.marketingStrategy = marketingStrategy;
    }

    public MarketingStrategy getStrategy(){
        return this.marketingStrategy;
    }

}
