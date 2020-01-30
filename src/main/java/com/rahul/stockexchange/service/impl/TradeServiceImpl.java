package com.rahul.stockexchange.service.impl;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.dao.TradeDAO;
import com.rahul.stockexchange.domain.Trade;
import com.rahul.stockexchange.domain.TradeType;
import com.rahul.stockexchange.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {
    Logger logger = LoggerFactory.getLogger(TradeServiceImpl.class);

    private TradeDAO tradeDAO;

    private ReferenceDataDAO referenceDataDAO;

    @Autowired
    public TradeServiceImpl(TradeDAO tradeDAO, ReferenceDataDAO referenceDataDAO) {
        this.tradeDAO = tradeDAO;
        this.referenceDataDAO = referenceDataDAO;
    }

    @Override
    public Trade createTrade(String ticker, Long quantity, BigDecimal price, TradeType type) {
        if (!referenceDataDAO.isListed(ticker))
            throw new IllegalArgumentException("Ticker " + ticker + " is not listed on the Exchange");

        Trade trade = Trade.getTradeBuilder()
                .ticker(ticker)
                .quantity(quantity)
                .price(price)
                .type(type)
                .build();

        tradeDAO.save(trade);
        logger.info("Trade created: {}", trade);
        return trade;
    }

    @Override
    public List<Trade> getTrades(String ticker) {
        List<Trade> trades = tradeDAO.getTrades(ticker);
        logger.info("{} trades retrieved for ticker {}", trades.size(), ticker);
        return trades;
    }

    @Override
    public Map<String, List<Trade>> getAllTrades() {
        Map<String, List<Trade>> allTrades = tradeDAO.getAllTrades();
        logger.info("Trades retrieved for {} tickers", allTrades.size());
        return allTrades;
    }

    @Override
    public BigDecimal getVWAP(String ticker, Integer durationMins) {
        if (ticker == null || ticker.isEmpty()) {
            throw new IllegalArgumentException("Invalid ticker " + ticker);
        }

        if (durationMins == null || durationMins <= 0) {
            logger.info("Defaulting to durationMins {}", durationMins);
            durationMins = 10;
        }

        List<Trade> trades = tradeDAO.getTrades(ticker);
        if(trades == null || trades.isEmpty()) {
            return BigDecimal.ZERO;
        }

        LocalDateTime tenMinsBefore = LocalDateTime.now().minusMinutes(durationMins);

        //TODO: should only BUY side of trades be considered?
        List<Trade> recentTrades = trades.stream()
                .filter(t -> t.getTimestamp().isAfter(tenMinsBefore))
                .collect(Collectors.toList());

        if(recentTrades == null || recentTrades.isEmpty()) {
            return BigDecimal.ZERO;
        }

        long quantity = 0;
        BigDecimal notional = BigDecimal.ZERO;
        for (Trade trade : recentTrades) {
            notional = notional.add(trade.getPrice().multiply(BigDecimal.valueOf(trade.getQuantity())));
            quantity += trade.getQuantity();
        }

        if (quantity == 0) {
            logger.error("Invalid trades found with 0 volumes for ticker {}", ticker);
            throw new IllegalStateException("Invalid trades found with 0 volumes for ticker " + ticker);
        }
        return notional.divide(BigDecimal.valueOf(quantity), 4, BigDecimal.ROUND_UP);
    }

    public BigDecimal computeIndex() {
        BigDecimal allVWAP = BigDecimal.ONE;
        for(Map.Entry<String, List<Trade>> tradesPerTicker : tradeDAO.getAllTrades().entrySet()) {
            allVWAP = allVWAP.multiply(getVWAP(tradesPerTicker.getKey(), 10));
        }
        return BigDecimal.valueOf(Math.sqrt(allVWAP.doubleValue())).setScale(4, BigDecimal.ROUND_UP);
    }
}
