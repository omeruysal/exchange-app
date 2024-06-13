package com.eva.exchange.impl;

import com.eva.exchange.entity.Portfolio;
import com.eva.exchange.entity.Share;
import com.eva.exchange.entity.User;
import com.eva.exchange.exception.EligibilityException;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.service.BuyService;
import com.eva.exchange.service.PortfolioService;
import com.eva.exchange.service.PortfolioShareService;
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
public class BuyServiceImpl implements BuyService {

    public static final String BALANCE_IS_NOT_ENOUGH = "User doesn't have enough balance - userId :%s, balance :%s, total amount :%s";
    public static final String USER_DOESN_T_HAVE_ENOUGH_BALANCE = "User doesn't have enough balance";
    public static final String EXCEEDS_REMAINING_COUNT_OF_THE_SHARE = "Exceeds remaining count of the share - quantity :%s, remaining count :%s";
    public static final String NO_ENOUGH_UNIT_OF_THE_SHARE_TO_BUY = "There is no enough unit of the share to buy";
    public static final String PURCHASED_S_UNITS_OF_SHARE_WITH_THE_SHARE_ID_S = "User with the userId :%s purchased :%s units of share with the shareId :%s";
    private final ShareService shareService;

    private final PortfolioService portfolioService;

    private final SharePriceService sharePriceService;

    private final TradeService tradeService;

    private final UserService userService;

    private final PortfolioShareService portfolioShareService;

    @Override
    @Transactional // to provide atomicity we need to add Transactional annotation
    public void buy(@Valid BuyRequest buyRequest) {
        // fetching necessary entities
        Share share = shareService.getShare(buyRequest.getShareId());

        User authenticatedUser = userService.getAuthenticatedUser();

        Portfolio portfolio = portfolioService.getPortfolio(authenticatedUser.getId());

        Double totalAmount = calculateTotalAmount(buyRequest);

        // check BUY operation can be applied or not
        checkEligibilityOfBuyOperation(buyRequest, share, authenticatedUser, totalAmount);

        // BUY operation and updating necessary entities
        executeBuyOperation(buyRequest, share, authenticatedUser, portfolio, totalAmount);


    }

    private static void checkEligibilityOfBuyOperation(BuyRequest buyRequest, Share share, User authenticatedUser, Double totalAmount) {
        boolean isBalanceEligible = authenticatedUser.getBalance() >= totalAmount;
        if (!isBalanceEligible) {
            log.error(String.format(BALANCE_IS_NOT_ENOUGH,
                    authenticatedUser.getId(), authenticatedUser.getBalance(), totalAmount));
            throw new EligibilityException(USER_DOESN_T_HAVE_ENOUGH_BALANCE);
        }

        boolean isShareRemainingCountEligible = buyRequest.getQuantity() <= share.getRemainingCount();
        if (!isShareRemainingCountEligible) {
            log.error(String.format(EXCEEDS_REMAINING_COUNT_OF_THE_SHARE,
                    buyRequest.getQuantity(), share.getRemainingCount()));
            throw new EligibilityException(NO_ENOUGH_UNIT_OF_THE_SHARE_TO_BUY);
        }
    }

    private void executeBuyOperation(BuyRequest buyRequest, Share share, User authenticatedUser, Portfolio portfolio, Double totalAmount) {
        portfolioShareService.updatePortfolioShareForBuy(portfolio, share, buyRequest.getQuantity());

        tradeService.createNewTrade(buyRequest, share, portfolio, totalAmount);

        userService.updateUserBalance(authenticatedUser, authenticatedUser.getBalance() - totalAmount);

        shareService.updateShareRemainingCount(buyRequest, share);

        log.info(String.format(PURCHASED_S_UNITS_OF_SHARE_WITH_THE_SHARE_ID_S,
                authenticatedUser.getId(), buyRequest.getQuantity(), buyRequest.getShareId()));
    }

    private Double calculateTotalAmount(BuyRequest buyRequest) {
        Double latestPrice = sharePriceService.findLatestPrice(buyRequest.getShareId());
        return buyRequest.getQuantity() * latestPrice;
    }
}
