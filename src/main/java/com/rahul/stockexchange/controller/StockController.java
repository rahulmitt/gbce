package com.rahul.stockexchange.controller;

import com.rahul.stockexchange.domain.Stock;
import com.rahul.stockexchange.dto.DividendYield;
import com.rahul.stockexchange.dto.PERatio;
import com.rahul.stockexchange.service.ComputationService;
import com.rahul.stockexchange.service.ReferenceDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/stock")
public class StockController {
    Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private ReferenceDataService referenceDataService;

    @Autowired
    private ComputationService computationService;

    @GetMapping("/list")
    public ResponseEntity<?> listAllStocks() {
        try {
            Map<String, Stock> stocks = referenceDataService.getAllListedStocks();
            logger.info("{} stocks retrieved", stocks.size());
            return ResponseEntity.status(HttpStatus.OK).body(stocks);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{ticker}/list")
    public ResponseEntity<?> listStock(@PathVariable String ticker) {
        logger.info("Stock listing for [{}] ticker requested", ticker);
        try {
            Stock stock = referenceDataService.getStock(ticker);
            logger.info("Stock retrieved {}", stock);
            return ResponseEntity.status(HttpStatus.OK).body(stock);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/yield/{ticker}/{price}")
    public ResponseEntity<?> computeDividendYield(@PathVariable String ticker, @PathVariable BigDecimal price) {
        logger.info("Computing dividend yield for ticker {} and price {}", ticker, price.toEngineeringString());

        try {
            BigDecimal dividendYield = computationService.computeDividendYield(ticker, price);
            logger.info("Dividend-Yield for {} ticker is {}", ticker, dividendYield.toPlainString());
            return ResponseEntity.status(HttpStatus.OK).body(new DividendYield(ticker, dividendYield));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/pe/{ticker}/{price}")
    public ResponseEntity<?> computePERatio(@PathVariable String ticker, @PathVariable BigDecimal price) {
        logger.info("Computing PE Ratio for ticker {} and price {}", ticker, price.toEngineeringString());

        try {
            BigDecimal peRatio = computationService.computePERatio(ticker, price);
            logger.info("PE Ratio for {} ticker is {}", ticker, peRatio.toPlainString());
            return ResponseEntity.status(HttpStatus.OK).body(new PERatio(ticker, peRatio));

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
