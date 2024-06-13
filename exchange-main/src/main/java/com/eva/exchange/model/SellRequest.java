package com.eva.exchange.model;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SellRequest extends ExchangeRequest {
    @Min(value = 1, message = "price should be greater than 0")
    private double price;
}
