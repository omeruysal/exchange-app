package com.eva.exchange.service;

import com.eva.exchange.entity.Share;
import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.model.SellRequest;

public interface ShareService {

    Share getShare(Long shareId);

    void save(Share share);

    void updateShareRemainingCountAndPrice(SellRequest sellRequest, Share share);

    void updateShareRemainingCount(BuyRequest buyRequest, Share share);
}
