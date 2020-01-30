package com.rahul.stockexchange.config;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.domain.Stock;
import com.rahul.stockexchange.domain.StockType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private ReferenceDataDAO referenceDataDAO;

    @Autowired
    public DataLoader(ReferenceDataDAO referenceDataDAO) {
        this.referenceDataDAO = referenceDataDAO;
    }


    @Override
    public void run(String... args) throws Exception {
        initialize();
    }

    private void initialize() {

        Stock tea = Stock.getStockBuilder()
                .ticker("TEA")
                .type(StockType.COMMON)
                .lastDividend(0)
                .fixedDividend(0)
                .parValue(100)
                .build();

        Stock pop = Stock.getStockBuilder()
                .ticker("POP")
                .type(StockType.COMMON)
                .lastDividend(8)
                .fixedDividend(0)
                .parValue(100)
                .build();

        Stock ale = Stock.getStockBuilder()
                .ticker("ALE")
                .type(StockType.COMMON)
                .lastDividend(23)
                .fixedDividend(0)
                .parValue(60)
                .build();

        Stock gin = Stock.getStockBuilder()
                .ticker("GIN")
                .type(StockType.PREFERRED)
                .lastDividend(8)
                .fixedDividend(2)
                .parValue(100)
                .build();

        Stock joe = Stock.getStockBuilder()
                .ticker("JOE")
                .type(StockType.COMMON)
                .lastDividend(13)
                .fixedDividend(0)
                .parValue(250)
                .build();

        List<Stock> stocks = Arrays.asList(tea, pop, ale, gin, joe);
        referenceDataDAO.addAllStocks(stocks);
    }
}
