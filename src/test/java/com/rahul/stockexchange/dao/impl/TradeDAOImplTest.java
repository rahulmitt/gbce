package com.rahul.stockexchange.dao.impl;

import com.rahul.stockexchange.dao.TradeDAO;
import com.rahul.stockexchange.domain.Trade;
import com.rahul.stockexchange.domain.TradeType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TradeDAOImplTest {

    private TradeDAO tradeDAO;

    private Trade trade1 = Trade.getTradeBuilder()
            .ticker("POP")
            .quantity(1000)
            .price(BigDecimal.valueOf(30))
            .type(TradeType.BUY)
            .build();

    private Trade trade2 = Trade.getTradeBuilder()
            .ticker("POP")
            .quantity(100)
            .price(BigDecimal.valueOf(50))
            .type(TradeType.BUY)
            .build();

    private Trade trade3 = Trade.getTradeBuilder()
            .ticker("POP")
            .quantity(500)
            .price(BigDecimal.valueOf(35))
            .type(TradeType.BUY)
            .build();

    private Trade trade4 = Trade.getTradeBuilder()
            .ticker("GIN")
            .quantity(550)
            .price(BigDecimal.valueOf(350))
            .type(TradeType.BUY)
            .build();

    @Before
    public void setup() {
        tradeDAO = new TradeDAOImpl();
    }

    @Test
    public void testSave() {
        tradeDAO.save(trade1);
        tradeDAO.save(trade2);
        tradeDAO.save(trade3);

        List<Trade> trades = tradeDAO.getTrades("POP");
        Assert.assertEquals(3, trades.size());
    }

    @Test
    public void testGetAllTrades() {
        tradeDAO.save(trade1);
        tradeDAO.save(trade2);
        tradeDAO.save(trade3);
        tradeDAO.save(trade4);

        Map<String, List<Trade>> allTrades = tradeDAO.getAllTrades();
        Assert.assertEquals(2, allTrades.size());
        Assert.assertEquals(3, allTrades.get("POP").size());
        Assert.assertEquals(1, allTrades.get("GIN").size());
    }
}
