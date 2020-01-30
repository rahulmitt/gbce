package com.rahul.stockexchange.dao;

import com.rahul.stockexchange.domain.Trade;

import java.util.List;
import java.util.Map;

public interface TradeDAO {
    void save(Trade trade);
    List<Trade> getTrades(String ticker);
    Map<String, List<Trade>> getAllTrades();
}
