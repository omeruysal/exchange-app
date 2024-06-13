package com.eva.exchange.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExchangeRequest {
    @NotNull(message = "shareId can't be null")
    private Long shareId;
    @Min(value = 1, message = "quantity should be greater than 0")
    private int quantity;
}
