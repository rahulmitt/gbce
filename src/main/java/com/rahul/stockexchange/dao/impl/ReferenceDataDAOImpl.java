package com.rahul.stockexchange.dao.impl;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReferenceDataDAOImpl implements ReferenceDataDAO {
    private static final Logger logger = LoggerFactory.getLogger(ReferenceDataDAOImpl.class);

    private static Map<String, Stock> repository;

    public ReferenceDataDAOImpl() {
        repository = new HashMap<>();
    }

    @Override
    public void addStock(Stock stock) {
        if (stock == null) {
            logger.error("Cannot add a null stock");
            throw new RuntimeException("Cannot accept null stock");
        }

        logger.info("Stock added with ticker symbol {} :: {}", stock.getTickerSymbol(), stock);
        repository.put(stock.getTickerSymbol(), stock);
    }

    @Override
    public void addAllStocks(List<Stock> stocks) {
        if (stocks == null) {
            logger.error("Cannot add null stocks");
            throw new RuntimeException("Cannot accept null stocks list");
        }

        for (Stock stock : stocks) {
            repository.put(stock.getTickerSymbol(), stock);
        }
    }

    @Override
    public Stock getStock(String tickerSymbol) {
        if (tickerSymbol == null || tickerSymbol.isEmpty()) {
            logger.error("Ticker symbol cannot be null");
            throw new RuntimeException("Invalid ticker Symbol");
        }
        Stock stock = repository.get(tickerSymbol);
        logger.info("[{}] found to be {}", tickerSymbol, stock);
        return stock;
    }

    @Override
    public Boolean isListed(String ticker) {
        return repository.containsKey(ticker);
    }

    @Override
    public Map<String, Stock> getAllListedStocks() {
        logger.info("Stocks retrieved for {} tickers {}", repository.size(), repository.keySet());
        return repository;
    }

    @Override
    public void removeStock(String ticker) {
        if (ticker == null || ticker.isEmpty()) {
            logger.error("Cannot add invalid ticker [{}]", ticker);
            throw new RuntimeException("Invalid ticker " + ticker + " cannot be removed");
        }

        if (!repository.containsKey(ticker)) {
            logger.error("Ticker [{}] not found", ticker);
        }

        logger.info("Ticker [{}] removed", ticker);
        repository.remove(ticker);
    }

    @Override
    public void removeAllStocks() {
        logger.info("All tickers removed");
        repository.clear();
    }
}
