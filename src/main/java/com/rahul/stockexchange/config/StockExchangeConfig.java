package com.rahul.stockexchange.config;

import com.rahul.stockexchange.dao.ReferenceDataDAO;
import com.rahul.stockexchange.dao.impl.ReferenceDataDAOImpl;
import com.rahul.stockexchange.service.ComputationService;
import com.rahul.stockexchange.service.ReferenceDataService;
import com.rahul.stockexchange.service.impl.ComputationServiceImpl;
import com.rahul.stockexchange.service.impl.ReferenceDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class StockExchangeConfig {

    @Autowired
    private Environment environment;

    @Bean(name = "referenceDataDAO")
    public ReferenceDataDAO referenceDataDAO() {
        return new ReferenceDataDAOImpl();
    }

    @Bean(name = "referenceDataService")
    public ReferenceDataService referenceDataService(ReferenceDataDAO referenceDataDAO) {
        return new ReferenceDataServiceImpl(referenceDataDAO);
    }

    @Bean(name = "computationService")
    public ComputationService computationService(ReferenceDataDAO referenceDataDAO) {
        return new ComputationServiceImpl(referenceDataDAO);
    }
}
