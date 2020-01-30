package com.rahul.stockexchange.service.impl;

import com.rahul.stockexchange.dao.impl.ReferenceDataDAOImpl;
import com.rahul.stockexchange.domain.Stock;
import com.rahul.stockexchange.domain.StockType;
import com.rahul.stockexchange.service.ReferenceDataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

public class ReferenceDataServiceImplTest {

    private ReferenceDataService referenceDataService;

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
        referenceDataService = new ReferenceDataServiceImpl(new ReferenceDataDAOImpl());
    }

    @Test
    public void testAddStock() {


        referenceDataService.addStock(pop);
        Stock expected = referenceDataService.getStock("POP");

        Assert.assertNotNull(expected);
        Assert.assertEquals("POP", expected.getTickerSymbol());
        Assert.assertEquals(StockType.COMMON, expected.getType());
        Assert.assertEquals(8, expected.getLastDividend().intValue());
        Assert.assertEquals(100, expected.getParValue().intValue());
    }

    @Test
    public void testIsListed() {
        referenceDataService.addStock(pop);
        boolean expected = referenceDataService.isListed("POP");
        Assert.assertTrue(expected);
    }

    @Test
    public void testGetAllListedStocks() {
        referenceDataService.addStock(pop);
        referenceDataService.addStock(gin);

        Map<String, Stock> expected = referenceDataService.getAllListedStocks();
        Assert.assertNotNull(expected);
        Assert.assertEquals(2, expected.size());
        Assert.assertEquals("POP", expected.get("POP").getTickerSymbol());
        Assert.assertEquals("GIN", expected.get("GIN").getTickerSymbol());
    }

    @Test
    public void testDelete() {
        referenceDataService.addStock(pop);
        referenceDataService.addStock(gin);

        Assert.assertEquals(2, referenceDataService.getAllListedStocks().size());

        referenceDataService.deleteStock("GIN");
        Assert.assertEquals(1, referenceDataService.getAllListedStocks().size());
        Assert.assertFalse(referenceDataService.isListed("GIN"));
        Assert.assertTrue(referenceDataService.isListed("POP"));
    }
}
