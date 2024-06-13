package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.Share;
import com.eva.exchange.entity.SharePrice;
import com.eva.exchange.entity.Trade;
import com.eva.exchange.entity.User;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.model.SellRequest;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SellServiceTest {

    @InjectMocks
    private SellServiceImpl sellService;

    @Mock
    private TradeService tradeService;

    @Mock
    private ShareService shareService;

    @Mock
    private SharePriceService sharePriceService;

    @Mock
    private UserService userService;

    @Mock
    private PortfolioService portfolioService;

    @Mock
    private PortfolioShareService portfolioShareService;

    private SellRequest sellRequest;
    private Share share;
    private Portfolio portfolio;

    private SharePrice sharePrice;

    private User user;

    @BeforeEach
    void init() {
        sellRequest = new SellRequest();
        sellRequest.setQuantity(1);
        sellRequest.setShareId(1L);

        sharePrice = new SharePrice();
        sharePrice.setPrice(10D);
        sharePrice.setShare(share);
        sharePrice.setId(1L);

        share = new Share();
        share.setId(1L);
        share.setName("test-name");
        share.setRemainingCount(2);
        share.setRate(10.0);
        share.setSymbol("TST");
        share.setSharePrices(new ArrayList<>());
        share.getSharePrices().add(sharePrice);

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
    void givenExistsShareId_whenGetPortfolio_thenReturnPortfolio() {
        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(shareService.getShare(anyLong())).thenReturn(share);
        when(portfolioService.getPortfolio(anyLong())).thenReturn(portfolio);
        when(portfolioShareService.isEligibleForSell(any(), any(), anyInt())).thenReturn(true);

        sellService.sell(sellRequest);

        verify(tradeService, times(1))
                .createNewTrade(any(SellRequest.class), any(Share.class), any(Portfolio.class), anyDouble());
    }

}
