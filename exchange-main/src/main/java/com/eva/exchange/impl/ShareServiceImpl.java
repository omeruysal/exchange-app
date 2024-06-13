package com.eva.exchange.impl;

import com.eva.exchange.entity.Share;
import com.eva.exchange.entity.SharePrice;
import com.eva.exchange.exception.RecordNotFoundException;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.model.SellRequest;
import com.eva.exchange.repository.ShareRepository;
import com.eva.exchange.service.ShareService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ShareServiceImpl implements ShareService {
    private final ShareRepository shareRepository;

    @Override
    public Share getShare(Long shareId) {
        Optional<Share> shareOptional = shareRepository.findById(shareId);
        if (shareOptional.isEmpty()) {
            log.error("Share does not exist with the given shareId : " + shareId);
            throw new RecordNotFoundException("Share Not Found");
        }
        return shareOptional.get();
    }

    @Override
    public void updateShareRemainingCountAndPrice(SellRequest sellRequest, Share share) {
        share.setRemainingCount(share.getRemainingCount() + sellRequest.getQuantity());
        share.getSharePrices().add(new SharePrice(share, sellRequest.getPrice(), Instant.now()));
        save(share);
    }

    @Override
    public void updateShareRemainingCount(BuyRequest buyRequest, Share share) {
        share.setRemainingCount(share.getRemainingCount() - buyRequest.getQuantity());
        save(share);
    }

    @Override
    public void save(Share share) {
        shareRepository.save(share);
    }
}
