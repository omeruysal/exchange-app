package com.eva.exchange.controller;

import com.eva.exchange.model.BuyRequest;
import com.eva.exchange.service.BuyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/")
@AllArgsConstructor
public class BuyController {

    private final BuyService buyService;

    @PostMapping(value = "buy")
    public ResponseEntity<String> buy(@RequestBody @Valid BuyRequest exchangeRequest) {
        buyService.buy(exchangeRequest);
        return ResponseEntity.ok("Buy operation is successfully.");
    }

}
