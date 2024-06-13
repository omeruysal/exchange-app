package com.eva.exchange.controller;

import com.eva.exchange.model.SellRequest;
import com.eva.exchange.service.SellService;
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
public class SellController {

    private final SellService sellService;

    @PostMapping(value = "sell")
    public ResponseEntity<String> sell(@RequestBody @Valid SellRequest sellRequest) {
         sellService.sell(sellRequest);
        return ResponseEntity.ok("Sell operation is successfully.");
    }
}
