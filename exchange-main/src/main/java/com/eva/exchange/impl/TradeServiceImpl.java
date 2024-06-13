package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.Share;
import com.eva.exchange.entity.Trade;
import com.eva.exchange.entity.TradeType;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.model.SellRequest;
import com.eva.exchange.repository.TradeRepository;
import com.eva.exchange.service.TradeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Override
    public boolean isEligibleForSell(SellRequest sellRequest, Long userId) {
        List<Object[]> groupedTradesByUserId = tradeRepository
                .findGroupedTradesByUserIdAndShareId(userId, sellRequest.getShareId());

        Long quantityForBuy = groupedTradesByUserId.stream()
                .filter(t -> t[0].equals(TradeType.BUY)).map(t -> (Long) t[1]).findFirst().orElse(0L);

        Long quantityForSell = groupedTradesByUserId.stream()
                .filter(t -> t[0].equals(TradeType.SELL)).map(t -> (Long) t[1]).findFirst().orElse(0L);

        return quantityForBuy - quantityForSell >= sellRequest.getQuantity();
    }


    @Override
    public void createNewTrade(SellRequest sellRequest, Share share, Portfolio portfolio, Double amount) {
        Trade trade = new Trade(portfolio, share, Instant.now(), amount, TradeType.SELL, sellRequest.getQuantity());
        save(trade);
    }

    @Override
    public void createNewTrade(BuyRequest buyRequest, Share share, Portfolio portfolio, Double amount) {
        Trade trade = new Trade(portfolio, share, Instant.now(), amount, TradeType.BUY, buyRequest.getQuantity());
        save(trade);
    }

    @Override
    public void save(Trade trade) {
        tradeRepository.save(trade);
    }
}
