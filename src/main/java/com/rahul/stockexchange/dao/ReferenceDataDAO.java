package com.rahul.stockexchange.dao;

import com.rahul.stockexchange.domain.Stock;

import java.util.List;
import java.util.Map;

public interface ReferenceDataDAO {

    void addStock(Stock stock);

    void addAllStocks(List<Stock> stocks);

    Stock getStock(String tickerSymbol);

    Boolean isListed(String ticker);

    Map<String, Stock> getAllListedStocks();

    void removeStock(String ticker);

    void removeAllStocks();
}
