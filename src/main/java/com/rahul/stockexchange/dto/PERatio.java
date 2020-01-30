package com.rahul.stockexchange.dto;

import java.math.BigDecimal;

public class PERatio {
    private String ticker;
    private BigDecimal peRatio;

    public PERatio(String ticker, BigDecimal peRatio) {
        this.ticker = ticker;
        this.peRatio = peRatio;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getPeRatio() {
        return peRatio;
    }

    @Override
    public String toString() {
        return "PERatio{" +
                "ticker='" + ticker + '\'' +
                ", peRatio=" + peRatio +
                '}';
    }
}
