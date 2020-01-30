package com.rahul.stockexchange.service.impl;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.domain.Stock;
import com.rahul.stockexchange.domain.StockType;
import com.rahul.stockexchange.service.ComputationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class ComputationServiceImplTest {

    private ComputationService computationService;

    @Before
    public void setup() {
        Stock pop = Stock.getStockBuilder()
                .ticker("POP")
                .type(StockType.COMMON)
                .lastDividend(8)
                .parValue(100)
                .build();

        Stock gin = Stock.getStockBuilder()
                .ticker("GIN")
                .type(StockType.PREFERRED)
                .lastDividend(8)
                .fixedDividend(2)
                .parValue(100)
                .build();

        ReferenceDataDAO referenceDataDAO = Mockito.mock(ReferenceDataDAO.class);
        Mockito.when(referenceDataDAO.getStock("POP")).thenReturn(pop);
        Mockito.when(referenceDataDAO.getStock("GIN")).thenReturn(gin);
        computationService = new ComputationServiceImpl(referenceDataDAO);
    }

    @Test
    public void testComputeDividendYieldForCommonStock() {
        BigDecimal yield = computationService.computeDividendYield("POP", BigDecimal.valueOf(4));
        Assert.assertNotNull(yield);
        Assert.assertEquals(2, yield.intValue());
    }

    @Test
    public void testComputeDividendYieldForPreferredStock() {
        BigDecimal yield = computationService.computeDividendYield("GIN", BigDecimal.valueOf(20));
        Assert.assertNotNull(yield);
        Assert.assertEquals(10, yield.intValue());
    }

    @Test
    public void testPE() {
        BigDecimal pe = computationService.computePERatio("POP", BigDecimal.valueOf(4));
        Assert.assertNotNull(pe);
        Assert.assertTrue(pe.compareTo(BigDecimal.valueOf(0.5d)) == 0);
    }

}
