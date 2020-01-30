package com.rahul.stockexchange.service.impl;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.domain.Stock;
import com.rahul.stockexchange.domain.StockType;
import com.rahul.stockexchange.service.ComputationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.function.Predicate;

@Service
public class ComputationServiceImpl implements ComputationService {

    private ReferenceDataDAO referenceDataDAO;

    private Predicate<String> invalidTicker = t -> t == null || t.isEmpty();

    private Predicate<BigDecimal> invalidPrice = p -> p == null || p.compareTo(BigDecimal.ZERO) <= 0;

    private Predicate<Stock> invalidStock = s -> s == null;

    private Predicate<Stock> invalidDividend = s -> s == null || s.getLastDividend().compareTo(BigDecimal.ZERO) <= 0;

    @Autowired
    public ComputationServiceImpl(ReferenceDataDAO referenceDataDAO) {
        this.referenceDataDAO = referenceDataDAO;
    }

    @Override
    public BigDecimal computeDividendYield(String ticker, BigDecimal price) {
        if (invalidTicker.test(ticker)) {
            throw new IllegalArgumentException("Invalid ticker  " + ticker);
        }

        if (invalidPrice.test(price)) {
            throw new IllegalArgumentException("Invalid price " + price.toPlainString());
        }

        Stock stock = referenceDataDAO.getStock(ticker);
        if (invalidStock.test(stock)) throw new RuntimeException("Stock not found for ticker " + ticker);

        if (stock.getType() == StockType.COMMON) {
            return stock.getLastDividend()
                    .divide(price, 4, BigDecimal.ROUND_UP);
        }

        if (stock.getType() == StockType.PREFERRED) {
            return stock.getFixedDividend()
                    .multiply(stock.getParValue())
                    .divide(price, 4, BigDecimal.ROUND_UP);
        }

        throw new RuntimeException("Invalid stock type " + stock.getType());
    }

    @Override
    public BigDecimal computePERatio(String ticker, BigDecimal price) {
        if (invalidTicker.test(ticker)) {
            throw new IllegalArgumentException("Invalid ticker  " + ticker);
        }

        if (invalidPrice.test(price)) {
            throw new IllegalArgumentException("Invalid price " + price.toPlainString());
        }

        Stock stock = referenceDataDAO.getStock(ticker);
        if(invalidDividend.test(stock)) throw new RuntimeException("Invalid dividend");

        return price.divide(stock.getLastDividend(), 4, BigDecimal.ROUND_UP);
    }
}
