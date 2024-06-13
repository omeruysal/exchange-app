package com.eva.exchange.impl;

import com.eva.exchange.entity.TradeType;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.model.SellRequest;
import com.eva.exchange.repository.TradeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @InjectMocks
    private TradeServiceImpl tradeService;

    @Mock
    private TradeRepository tradeRepository;
    private List<Object[]> groupedTradesByUserId;

    private SellRequest sellRequest;

    @BeforeEach
    void init() {
        sellRequest = new SellRequest();
        sellRequest.setQuantity(1);
        sellRequest.setShareId(1L);

        Object[] objArray = new Object[2];
        objArray[0] = TradeType.BUY;
        objArray[1] = 19L;

        groupedTradesByUserId = new ArrayList<>();
        groupedTradesByUserId.add(objArray);
    }

    @Test
    void givenExistsShareId_whenGetPortfolio_thenReturnPortfolio() {
        when(tradeRepository.findGroupedTradesByUserIdAndShareId(anyLong(), anyLong())).thenReturn(groupedTradesByUserId);

        boolean result = tradeService.isEligibleForSell(sellRequest, 1L);

        assertTrue(result);
    }

}
