package com.rahul.stockexchange.domain;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TradeBuilderTest {

    @Test
    public void testTradeBuilder() {
        Trade trade = Trade.getTradeBuilder()
                .ticker("POP")
                .quantity(1000)
                .price(BigDecimal.valueOf(30))
                .type(TradeType.BUY)
                .build();

        Assert.assertNotNull(trade);
        Assert.assertEquals("POP", trade.getTicker());
        Assert.assertEquals(1000L, trade.getQuantity().longValue());
        Assert.assertEquals(30, trade.getPrice().intValue());
        Assert.assertEquals(TradeType.BUY, trade.getType());
    }
}
