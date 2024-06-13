package com.eva.exchange.impl;

import com.eva.exchange.entity.Share;
import com.eva.exchange.exception.RecordNotFoundException;
import com.eva.exchange.repository.ShareRepository;
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
class ShareServiceTest {

    @InjectMocks
    private ShareServiceImpl shareService;

    @Mock
    private ShareRepository shareRepository;

    private Share share;

    @BeforeEach
    void init() {

        share = new Share();
        share.setId(1L);
        share.setName("test-name");
        share.setRemainingCount(2);
        share.setRate(10.0);
        share.setSymbol("TST");
    }

    @Test
    void givenExistsShareId_whenGetPortfolio_thenReturnPortfolio() {
        when(shareRepository.findById(anyLong())).thenReturn(Optional.of(share));

        Share result = shareService.getShare(1L);

        assertEquals(share.getId(), result.getId());
    }

    @Test
    void givenNotExistShareId_whenGetPortfolio_thenThrowException() {
        when(shareRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, ()->{
            shareService.getShare(1L);
        });
    }

}
