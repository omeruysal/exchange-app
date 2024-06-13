package com.eva.exchange.service;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.Share;

public interface PortfolioShareService {
    void updatePortfolioShareForBuy(Portfolio portfolio, Share share, Integer quantity);
    void updatePortfolioShareForSell(Portfolio portfolio, Share share, Integer quantity);
    boolean isEligibleForSell(Portfolio portfolio, Share share, Integer quantity);
}
