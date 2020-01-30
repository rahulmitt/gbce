package com.rahul.stockexchange.util;

import com.rahul.stockexchange.domain.Trade;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class Validations {

    public static final Predicate<Trade> invalidTrade = t -> t == null
            || t.getTicker() == null || t.getTicker().isEmpty()
            || t.getType() == null
            || t.getPrice() == null || t.getPrice().compareTo(BigDecimal.ZERO) <=0
            || t.getQuantity() == null || t.getQuantity() <= 0;
}
