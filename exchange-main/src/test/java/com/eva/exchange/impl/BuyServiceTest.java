package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.Share;
import com.eva.exchange.entity.Trade;
import com.eva.exchange.entity.User;
import com.eva.exchange.exception.EligibilityException;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.service.PortfolioService;
import com.eva.exchange.service.PortfolioShareService;
import com.eva.exchange.service.SharePriceService;
import com.eva.exchange.service.ShareService;
import com.eva.exchange.service.TradeService;
import com.eva.exchange.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuyServiceTest {

    @InjectMocks
    private BuyServiceImpl buyService;
    @Mock
    private ShareService shareService;
    @Mock
    private PortfolioService portfolioService;
    @Mock
    private SharePriceService sharePriceService;
    @Mock
    private TradeService tradeService;

    @Mock
    private UserService userService;

    @Mock
    private PortfolioShareService portfolioShareService;

    private BuyRequest exchangeRequest;
    private Share share;
    private Portfolio portfolio;
    private User user;

    @BeforeEach
    void init() {
        exchangeRequest = new BuyRequest();
        exchangeRequest.setQuantity(1);
        exchangeRequest.setShareId(1L);

        share = new Share();
        share.setId(1L);
        share.setName("test-name");
        share.setRemainingCount(2);
        share.setRate(10.0);
        share.setSymbol("TST");

        user = new User();
        user.setId(1L);
        user.setName("test-name");
        user.setEmail("test-email");
        user.setBalance(100.0);

        portfolio = new Portfolio();
        portfolio.setId(1L);
        portfolio.setUser(user);
    }

    @Test
    void givenValidRequest_whenBuy_thenSaveTrade() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(shareService.getShare(anyLong())).thenReturn(share);
        when(portfolioService.getPortfolio(anyLong())).thenReturn(portfolio);
        when(sharePriceService.findLatestPrice(anyLong())).thenReturn(10D);

        buyService.buy(exchangeRequest);

        verify(tradeService, times(1))
                .createNewTrade(any(BuyRequest.class), any(Share.class), any(Portfolio.class), anyDouble());
    }

    @Test
    void givenInadequateBalance_whenBuy_thenThrowException() {
        user.setBalance(0D);
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(shareService.getShare(anyLong())).thenReturn(share);
        when(portfolioService.getPortfolio(anyLong())).thenReturn(portfolio);
        when(sharePriceService.findLatestPrice(anyLong())).thenReturn(10D);

        assertThrows(EligibilityException.class, () -> {
            buyService.buy(exchangeRequest);
        });

        verify(tradeService, times(0)).save(any(Trade.class));
    }

    @Test
    void givenUnexpectedQuantity_whenBuy_thenThrowError() {
        share.setRemainingCount(0);
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(shareService.getShare(anyLong())).thenReturn(share);
        when(portfolioService.getPortfolio(anyLong())).thenReturn(portfolio);
        when(sharePriceService.findLatestPrice(anyLong())).thenReturn(10D);

        assertThrows(EligibilityException.class, () -> {
            buyService.buy(exchangeRequest);
        });

        verify(tradeService, times(0)).save(any(Trade.class));
    }
}
