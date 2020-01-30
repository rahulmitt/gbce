package com.rahul.stockexchange.domain;

import java.math.BigDecimal;

//TODO: make it immutable
public class Stock {
    private String tickerSymbol;
    private StockType type;
    private BigDecimal lastDividend;
    private BigDecimal fixedDividend;
    private BigDecimal parValue;

    private Stock() {
    }

    public String getTickerSymbol() {
        return tickerSymbol;
    }

    public StockType getType() {
        return type;
    }

    public BigDecimal getLastDividend() {
        return lastDividend == null ? BigDecimal.ZERO : lastDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend == null ? BigDecimal.ZERO : fixedDividend;
    }

    public BigDecimal getParValue() {
        return parValue == null ? BigDecimal.ZERO : parValue;
    }

    @Override
    public String toString() {
        return "{" +
                "tickerSymbol='" + getTickerSymbol() + '\'' +
                ", type=" + getType() +
                ", lastDividend=" + getLastDividend() +
                ", fixedDividend=" + getFixedDividend() +
                ", parValue=" + getParValue() +
                '}';
    }

    public static StockBuilder getStockBuilder() {
        return new StockBuilder();
    }

    public static class StockBuilder {
        private Stock dto;

        public StockBuilder() {
            dto = new Stock();
        }

        public StockBuilder ticker(String ticker) {
            dto.tickerSymbol = ticker;
            return this;
        }

        public StockBuilder type(StockType type) {
            dto.type = type;
            return this;
        }

        public StockBuilder lastDividend(double lastDividend) {
            dto.lastDividend = BigDecimal.valueOf(lastDividend);
            return this;
        }

        public StockBuilder fixedDividend(double fixedDividend) {
            dto.fixedDividend = BigDecimal.valueOf(fixedDividend);
            return this;
        }

        public StockBuilder parValue(double parValue) {
            dto.parValue = BigDecimal.valueOf(parValue);
            return this;
        }

        public Stock build() {
            return dto;
        }
    }
}


