package com.rahul.stockexchange.service;

import java.math.BigDecimal;

public interface ComputationService {
    BigDecimal computeDividendYield(String ticker, BigDecimal price);
    BigDecimal computePERatio(String ticker, BigDecimal price);
}
