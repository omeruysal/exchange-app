package com.eva.exchange.service;

import com.eva.exchange.model.BuyRequest;
import jakarta.validation.Valid;

public interface BuyService {
    void buy(@Valid BuyRequest exchangeRequest);
}
