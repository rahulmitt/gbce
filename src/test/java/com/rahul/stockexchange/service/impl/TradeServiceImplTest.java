package com.rahul.stockexchange.service.impl;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.dao.TradeDAO;
import com.rahul.stockexchange.dao.impl.ReferenceDataDAOImpl;
import com.rahul.stockexchange.dao.impl.TradeDAOImpl;
import com.rahul.stockexchange.domain.Stock;
import com.rahul.stockexchange.domain.StockType;
import com.rahul.stockexchange.domain.Trade;
import com.rahul.stockexchange.domain.TradeType;
import com.rahul.stockexchange.service.TradeService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TradeServiceImplTest {

    private TradeService tradeService;

    private Stock pop = Stock.getStockBuilder()
            .ticker("POP")
            .type(StockType.COMMON)
            .lastDividend(8)
            .parValue(100)
            .build();

    private Stock gin = Stock.getStockBuilder()
            .ticker("GIN")
            .type(StockType.PREFERRED)
            .lastDividend(8)
            .fixedDividend(2)
            .parValue(100)
            .build();

    @Before
    public void setup() {
        TradeDAO tradeDAO = new TradeDAOImpl();
        ReferenceDataDAO referenceDataDAO = new ReferenceDataDAOImpl();
        referenceDataDAO.addStock(pop);
        referenceDataDAO.addStock(gin);

        tradeService = new TradeServiceImpl(tradeDAO, referenceDataDAO);
    }

    @Test
    public void testCreateTrade() {
        Trade trade = tradeService.createTrade("GIN", 100L, BigDecimal.valueOf(30), TradeType.BUY);
        Assert.assertNotNull(trade);
        Assert.assertEquals("GIN", trade.getTicker());
        Assert.assertEquals(100, trade.getQuantity().longValue());
        Assert.assertEquals(30, trade.getPrice().intValue());
        Assert.assertEquals(TradeType.BUY, trade.getType());
        Assert.assertNotNull(trade.getTimestamp());
    }

    @Test
    public void testGetTrades() {
        tradeService.createTrade("POP", 100L, BigDecimal.valueOf(30), TradeType.BUY);
        tradeService.createTrade("POP", 200L, BigDecimal.valueOf(25), TradeType.BUY);
        tradeService.createTrade("GIN", 1000L, BigDecimal.valueOf(10), TradeType.BUY);

        List<Trade> trades = tradeService.getTrades("POP");
        Assert.assertEquals(2, trades.size());
    }

    @Test
    public void testGetVWAP() {
        tradeService.createTrade("POP", 100L, BigDecimal.valueOf(30), TradeType.BUY);
        tradeService.createTrade("POP", 200L, BigDecimal.valueOf(35), TradeType.BUY);
        tradeService.createTrade("POP", 300L, BigDecimal.valueOf(40), TradeType.BUY);
        BigDecimal vwap = tradeService.getVWAP("POP", 10);
        Assert.assertNotNull(vwap);
        Assert.assertEquals(0, BigDecimal.valueOf(36.6667).compareTo(vwap));
    }

    @Test
    public void testComputeIndex() {
        tradeService.createTrade("POP", 100L, BigDecimal.valueOf(30), TradeType.BUY);
        tradeService.createTrade("POP", 200L, BigDecimal.valueOf(35), TradeType.BUY);
        tradeService.createTrade("POP", 300L, BigDecimal.valueOf(40), TradeType.BUY);

        tradeService.createTrade("GIN", 1000L, BigDecimal.valueOf(10), TradeType.BUY);
        tradeService.createTrade("GIN", 2000L, BigDecimal.valueOf(15), TradeType.BUY);
        tradeService.createTrade("GIN", 3000L, BigDecimal.valueOf(20), TradeType.BUY);

        BigDecimal index = tradeService.computeIndex();

        Assert.assertNotNull(index);
        Assert.assertEquals(0, BigDecimal.valueOf(24.7207).compareTo(index));
    }
}
