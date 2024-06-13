package com.eva.exchange.service;

import com.eva.exchange.entity.Portfolio;

public interface PortfolioService {

    void save(Portfolio portfolio);

    Portfolio getPortfolio(Long userId);

}
