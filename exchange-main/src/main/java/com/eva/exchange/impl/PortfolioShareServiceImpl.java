package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.PortfolioShare;
import com.eva.exchange.entity.Share;
import com.eva.exchange.repository.PortfolioShareRepository;
import com.eva.exchange.service.PortfolioShareService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PortfolioShareServiceImpl implements PortfolioShareService {

    private final PortfolioShareRepository portfolioShareRepository;

    @Override
    public void updatePortfolioShareForBuy(Portfolio portfolio, Share share, Integer quantity) {
        Optional<PortfolioShare> optionalPortfolioShare = portfolioShareRepository
                .findByPortfolio_IdAndShare_Id(portfolio.getId(), share.getId());

        PortfolioShare portfolioShare;

        if (optionalPortfolioShare.isPresent()) {
            portfolioShare = optionalPortfolioShare.get();
            portfolioShare.setQuantity(portfolioShare.getQuantity() + quantity);
        } else {
            portfolioShare = new PortfolioShare(portfolio, share, quantity);
        }

        portfolioShareRepository.save(portfolioShare);

    }


    @Override
    public void updatePortfolioShareForSell(Portfolio portfolio, Share share, Integer quantity) {
        Optional<PortfolioShare> optionalPortfolioShare = Optional.of(portfolioShareRepository
                .findByPortfolio_IdAndShare_Id(portfolio.getId(), share.getId()).orElseThrow());


        PortfolioShare portfolioShare = optionalPortfolioShare.get();
        Integer finalAmount = portfolioShare.getQuantity() - quantity;

        if (finalAmount == 0) {
            portfolioShareRepository.delete(portfolioShare);

        } else {
            portfolioShare.setQuantity(finalAmount);
            portfolioShareRepository.save(portfolioShare);
        }


    }

    @Override
    public boolean isEligibleForSell(Portfolio portfolio, Share share, Integer quantity) {
        Optional<PortfolioShare> optionalPortfolioShare = Optional.of(portfolioShareRepository
                .findByPortfolio_IdAndShare_Id(portfolio.getId(), share.getId()).orElseThrow());

        return optionalPortfolioShare.get().getQuantity() >= quantity;
    }
}
