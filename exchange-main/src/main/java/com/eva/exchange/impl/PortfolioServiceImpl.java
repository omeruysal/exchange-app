package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.exception.RecordNotFoundException;
import com.eva.exchange.repository.PortfolioRepository;
import com.eva.exchange.service.PortfolioService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public Portfolio getPortfolio(Long userId) {
        Optional<Portfolio> portfolioOptional = portfolioRepository.findByUser_id(userId);
        if (portfolioOptional.isEmpty()) {
            log.error("Portfolio does not exist with the given userId : " + userId);
            throw new RecordNotFoundException("Portfolio Not Found");
        }
        return portfolioOptional.get();
    }


    @Override
    public void save(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }
}
