package com.rahul.stockexchange.dto;

import java.math.BigDecimal;

public class DividendYield {
    private String ticker;
    private BigDecimal dividendYield;

    public DividendYield(String ticker, BigDecimal dividendYield) {
        this.ticker = ticker;
        this.dividendYield = dividendYield;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getDividendYield() {
        return dividendYield;
    }

    @Override
    public String toString() {
        return "DividendYield{" +
                "ticker='" + ticker + '\'' +
                ", dividendYield=" + dividendYield +
                '}';
    }
}
