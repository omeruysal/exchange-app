package com.eva.exchange.service;

import com.eva.exchange.model.SellRequest;
import jakarta.validation.Valid;

public interface SellService {
    void sell(@Valid SellRequest sellRequest);
}
