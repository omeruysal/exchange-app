package com.eva.exchange.service;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.Share;
import com.eva.exchange.entity.Trade;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.model.SellRequest;

public interface TradeService {
    boolean isEligibleForSell(SellRequest sellRequest, Long userId) ;

    void save(Trade trade);
    void createNewTrade(BuyRequest sellRequest, Share share, Portfolio portfolio, Double amount);


    void createNewTrade(SellRequest sellRequest, Share share, Portfolio portfolio, Double amount);
}
