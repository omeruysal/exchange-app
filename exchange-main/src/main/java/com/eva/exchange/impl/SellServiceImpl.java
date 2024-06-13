package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.Share;
import com.eva.exchange.entity.User;
import com.eva.exchange.exception.EligibilityException;
import com.eva.exchange.model.SellRequest;
import com.eva.exchange.service.PortfolioService;
import com.eva.exchange.service.PortfolioShareService;
import com.eva.exchange.service.SellService;
import com.eva.exchange.service.SharePriceService;
import com.eva.exchange.service.ShareService;
import com.eva.exchange.service.TradeService;
import com.eva.exchange.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class SellServiceImpl implements SellService {

    public static final String USER_WITH_THE_USER_ID_S_SOLD_S_UNITS_OF_SHARE = "User with the userId :%s sold :%s units of share with the shareId :%s";
    public static final String DOESN_T_HAVE_ENOUGH_UNITS_OF_THE_SHARE_TO_SELL_OPERATION = "User with the userId :%s doesn't have enough units of the share to sell operation";
    private final TradeService tradeService;

    private final ShareService shareService;

    private final SharePriceService sharePriceService;

    private final PortfolioService portfolioService;

    private final UserService userService;

    private final PortfolioShareService portfolioShareService;

    @Override
    @Transactional // to provide atomicity we need to add Transactional annotation
    public void sell(@Valid SellRequest sellRequest) {
        // fetching necessary entities
        Share share = shareService.getShare(sellRequest.getShareId());

        User authenticatedUser = userService.getAuthenticatedUser();

        Portfolio portfolio = portfolioService.getPortfolio(authenticatedUser.getId());

        // check SELL operation can be applied or not
        checkEligibilityOfSellOperation(portfolio, share, sellRequest.getQuantity(), authenticatedUser.getId());

        // SELL operation and updating necessary entities
        executeSellOperation(sellRequest, share, authenticatedUser, portfolio);
    }

    private void checkEligibilityOfSellOperation(Portfolio portfolio, Share share, Integer quantity, Long id) {
        boolean isEligibleForSell = portfolioShareService.isEligibleForSell(portfolio, share, quantity);
        if (!isEligibleForSell) {
            log.error(String.format(DOESN_T_HAVE_ENOUGH_UNITS_OF_THE_SHARE_TO_SELL_OPERATION, id));
            throw new EligibilityException(String.format(DOESN_T_HAVE_ENOUGH_UNITS_OF_THE_SHARE_TO_SELL_OPERATION, id));
        }
    }

    private void executeSellOperation(SellRequest sellRequest, Share share, User authenticatedUser, Portfolio portfolio) {
        Double amount = sellRequest.getPrice() * sellRequest.getQuantity();

        portfolioShareService.updatePortfolioShareForSell(portfolio, share, sellRequest.getQuantity());

        tradeService.createNewTrade(sellRequest, share, portfolio, amount);

        userService.updateUserBalance(authenticatedUser, authenticatedUser.getBalance() + sellRequest.getPrice());

        shareService.updateShareRemainingCountAndPrice(sellRequest, share);

        log.info(String.format(USER_WITH_THE_USER_ID_S_SOLD_S_UNITS_OF_SHARE,
                authenticatedUser.getId(), sellRequest.getQuantity(), sellRequest.getShareId()));
    }


}
