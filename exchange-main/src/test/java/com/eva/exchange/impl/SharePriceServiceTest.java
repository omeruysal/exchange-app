package com.eva.exchange.impl;

import com.eva.exchange.entity.SharePrice;
import com.eva.exchange.exception.RecordNotFoundException;
import com.eva.exchange.repository.SharePriceRepository;
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
class SharePriceServiceTest {

    public static final double PRICE = 10.0;
    @InjectMocks
    private SharePriceServiceImpl sharePriceService;

    @Mock
    private SharePriceRepository sharePriceRepository;

    private SharePrice sharePrice;

    @BeforeEach
    void init() {
        sharePrice = new SharePrice();
        sharePrice.setId(1L);
        sharePrice.setPrice(PRICE);
    }

    @Test
    void givenExistsShareId_whenGetPortfolio_thenReturnPortfolio() {
        when(sharePriceRepository.findFirstByShare_IdOrderByIdDesc(anyLong())).thenReturn(Optional.of(sharePrice));

        Double result = sharePriceService.findLatestPrice(1L);

        assertEquals(PRICE, result);
    }

    @Test
    void givenNotExistShareId_whenGetPortfolio_thenThrowException() {
        when(sharePriceRepository.findFirstByShare_IdOrderByIdDesc(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, ()->{
            sharePriceService.findLatestPrice(1L);
        });
    }

}
