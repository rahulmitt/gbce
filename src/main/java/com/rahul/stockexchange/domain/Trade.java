package com.rahul.stockexchange.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

//TODO: make it immutable
public final class Trade {
    private long tradeId;
    private String ticker;
    private Long quantity;
    private BigDecimal price;
    private TradeType type;
    private LocalDateTime timestamp;

    private static final AtomicLong counter = new AtomicLong(0);

    public long getTradeId() {
        return tradeId;
    }

    public String getTicker() {
        return ticker;
    }

    public Long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TradeType getType() {
        return type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "tradeId=" + tradeId +
                ", ticker='" + ticker + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", type=" + type +
                ", timestamp=" + timestamp +
                '}';
    }

    private Trade() {
        tradeId = counter.incrementAndGet();
    }

    public static TradeBuilder getTradeBuilder() {
        return new TradeBuilder();
    }

    public static class TradeBuilder {
        private Trade dto;

        public TradeBuilder() {
            dto = new Trade();
        }

        public TradeBuilder ticker(String ticker) {
            dto.ticker = ticker;
            return this;
        }

        public TradeBuilder quantity(long quantity) {
            dto.quantity = quantity;
            return this;
        }

        public TradeBuilder price(BigDecimal price) {
            dto.price = price;
            return this;
        }

        public TradeBuilder type(TradeType type) {
            dto.type = type;
            return this;
        }

        public Trade build() {
            dto.timestamp = LocalDateTime.now();
            return dto;
        }
    }
}
