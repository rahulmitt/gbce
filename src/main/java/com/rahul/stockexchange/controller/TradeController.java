package com.rahul.stockexchange.controller;

import com.rahul.stockexchange.domain.Trade;
import com.rahul.stockexchange.domain.TradeType;
import com.rahul.stockexchange.dto.VWAP;
import com.rahul.stockexchange.service.TradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trade")
public class TradeController {
    Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;

    @PostMapping("/create/ticker/{ticker}/type/{type}/quantity/{quantity}/price/{price}")
    public ResponseEntity<?> createTrade(
            @PathVariable String ticker,
            @PathVariable Long quantity,
            @PathVariable BigDecimal price,
            @PathVariable TradeType type
    ) {
        try {
            Trade trade = tradeService.createTrade(ticker, quantity, price, type);
            logger.info("Trade created {}", trade);
            return ResponseEntity.status(HttpStatus.OK).body(trade);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get/{ticker}")
    public ResponseEntity<?> getTrades(@PathVariable String ticker) {
        try {
            List<Trade> trades = tradeService.getTrades(ticker);
            logger.info("{} trades retrieved", trades.size());
            return ResponseEntity.status(HttpStatus.OK).body(trades);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllTrades() {
        try {
            Map<String, List<Trade>> allTrades = tradeService.getAllTrades();
            logger.info("Trades retrieved for {} tickers", allTrades.size());
            return ResponseEntity.status(HttpStatus.OK).body(allTrades);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/vwap/{ticker}")
    public ResponseEntity<?> vwap(@PathVariable String ticker) {

        try {
            BigDecimal vwap = tradeService.getVWAP(ticker, 10);
            logger.info("VWAP for ticker {} is {}", ticker, vwap.toPlainString());
            return ResponseEntity.status(HttpStatus.OK).body(new VWAP(ticker, vwap));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/index")
    public ResponseEntity<?> gbceAllShareIndex() {

        try {
            BigDecimal index = tradeService.computeIndex();
            logger.info("{} is the computed index", index.toPlainString());
            return ResponseEntity.status(HttpStatus.OK).body(index);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
