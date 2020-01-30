package com.rahul.stockexchange.service;

import com.rahul.stockexchange.domain.Stock;

import java.util.Map;

public interface ReferenceDataService {
    void addStock(Stock stock);
    Stock getStock(String ticker);
    Boolean isListed(String ticker);
    Map<String, Stock> getAllListedStocks();
    void deleteStock(String ticker);
}
