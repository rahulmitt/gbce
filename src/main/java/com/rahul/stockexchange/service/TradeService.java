package com.rahul.stockexchange.service;

import com.rahul.stockexchange.domain.Trade;
import com.rahul.stockexchange.domain.TradeType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TradeService {
    Trade createTrade(String ticker, Long quantity, BigDecimal price, TradeType type);
    List<Trade> getTrades(String ticker);
    Map<String, List<Trade>> getAllTrades();
    BigDecimal getVWAP(String ticker, Integer durationMins);
    BigDecimal computeIndex();
}
