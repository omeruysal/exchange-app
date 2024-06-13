package com.eva.exchange.impl;

import com.eva.exchange.entity.SharePrice;
import com.eva.exchange.exception.RecordNotFoundException;
import com.eva.exchange.repository.SharePriceRepository;
import com.eva.exchange.service.SharePriceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class SharePriceServiceImpl implements SharePriceService {

    private final SharePriceRepository sharePriceRepository;

    @Override
    public Double findLatestPrice(Long shareId) {
        Optional<SharePrice> latestPrice = sharePriceRepository.findFirstByShare_IdOrderByIdDesc(shareId);
        if (latestPrice.isEmpty()) {
            throw new RecordNotFoundException("The latest price Not Found for the share");
        }
        return latestPrice.get().getPrice();
    }
}
