package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.User;
import com.eva.exchange.exception.RecordNotFoundException;
import com.eva.exchange.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortfolioServiceTest {

    public static final long PORTFOLIO_ID = 1L;
    @InjectMocks
    private PortfolioServiceImpl portfolioService;

    @Mock
    private PortfolioRepository portfolioRepository;

    private Portfolio portfolio;

    @BeforeEach
    void init() {
        User user = new User();
        user.setId(PORTFOLIO_ID);
        user.setName("test-name");
        user.setEmail("test-email");
        user.setBalance(100.0);

        portfolio = new Portfolio();
        portfolio.setId(PORTFOLIO_ID);
        portfolio.setUser(user);
    }

    @Test
    void givenExistsUserId_whenGetPortfolio_thenReturnPortfolio() {
        when(portfolioRepository.findByUser_id(anyLong())).thenReturn(Optional.of(portfolio));

        Portfolio result = portfolioService.getPortfolio(1L);

        assertEquals(PORTFOLIO_ID, result.getId());
    }

    @Test
    void givenNotExistUserId_whenGetPortfolio_thenThrowException() {
        when(portfolioRepository.findByUser_id(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, ()->{
            portfolioService.getPortfolio(1L);
        });
    }

}
