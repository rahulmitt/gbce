package com.rahul.stockexchange.domain;

import org.junit.Assert;
import org.junit.Test;

public class StockBuilderTest {

    @Test
    public void testBuildStock() {
        Stock pop = Stock.getStockBuilder()
                .ticker("POP")
                .type(StockType.COMMON)
                .lastDividend(8)
                .fixedDividend(20)
                .parValue(100)
                .build();

        Assert.assertNotNull(pop);
        Assert.assertEquals("POP", pop.getTickerSymbol());
        Assert.assertEquals(StockType.COMMON, pop.getType());
        Assert.assertEquals(8, pop.getLastDividend().intValue());
        Assert.assertEquals(20, pop.getFixedDividend().intValue());
        Assert.assertEquals(100, pop.getParValue().intValue());
    }

    @Test
    public void testFixedDividendIsZeroEvenIfNotSet() {
        Stock pop = Stock.getStockBuilder()
                .ticker("POP")
                .type(StockType.COMMON)
                .build();

        Assert.assertNotNull(pop);
        Assert.assertEquals("POP", pop.getTickerSymbol());
        Assert.assertEquals(StockType.COMMON, pop.getType());
        Assert.assertEquals(0, pop.getLastDividend().intValue());
        Assert.assertEquals(0, pop.getFixedDividend().intValue());
        Assert.assertEquals(0, pop.getParValue().intValue());
    }
}
