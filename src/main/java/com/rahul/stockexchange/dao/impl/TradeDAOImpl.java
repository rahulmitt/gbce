package com.rahul.stockexchange.dao.impl;

import com.rahul.stockexchange.dao.TradeDAO;
import com.rahul.stockexchange.domain.Trade;
import com.rahul.stockexchange.util.Validations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TradeDAOImpl implements TradeDAO {
    Logger logger = LoggerFactory.getLogger(TradeDAOImpl.class);

    private static Map<String, List<Trade>> repository;

    public TradeDAOImpl() {
        repository = new HashMap<>();
    }

    @Override
    public void save(Trade trade) {
        if (Validations.invalidTrade.test(trade)) {
            logger.error("Invalid trade {}", trade);
            throw new IllegalArgumentException("Invalid Trade: " + trade);
        }

        String ticker = trade.getTicker();
        if (!repository.containsKey(ticker)) repository.put(ticker, new ArrayList<>());

        repository.get(ticker).add(trade);
    }

    @Override
    public List<Trade> getTrades(String ticker) {
        List<Trade> trades = repository.get(ticker);
        return trades == null ? new ArrayList<>() : trades;
    }

    @Override
    public Map<String, List<Trade>> getAllTrades() {
        return repository;
    }
}
