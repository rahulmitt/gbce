package com.rahul.stockexchange.service.impl;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.domain.Stock;
import com.rahul.stockexchange.service.ReferenceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {

    private ReferenceDataDAO referenceDataDAO;

    @Autowired
    public ReferenceDataServiceImpl(ReferenceDataDAO referenceDataDAO) {
        this.referenceDataDAO = referenceDataDAO;
    }

    @Override
    public void addStock(Stock stock) {
        referenceDataDAO.addStock(stock);
    }

    @Override
    public Stock getStock(String ticker) {
        return referenceDataDAO.getStock(ticker);
    }

    @Override
    public Boolean isListed(String ticker) {
        return referenceDataDAO.isListed(ticker);
    }

    @Override
    public Map<String, Stock> getAllListedStocks() {
        return referenceDataDAO.getAllListedStocks();
    }

    @Override
    public void deleteStock(String ticker) {
        referenceDataDAO.removeStock(ticker);
    }
}
