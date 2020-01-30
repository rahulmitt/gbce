package com.rahul.stockexchange.dto;

import java.math.BigDecimal;

public class VWAP {
    private String ticker;
    private BigDecimal vwap;

    public VWAP(String ticker, BigDecimal vwap) {
        this.ticker = ticker;
        this.vwap = vwap;
    }

    public String getTicker() {
        return ticker;
    }

    public BigDecimal getVwap() {
        return vwap;
    }

    @Override
    public String toString() {
        return "VWAP{" +
                "ticker='" + ticker + '\'' +
                ", vwap=" + vwap +
                '}';
    }
}
