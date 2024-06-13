package com.eva.exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name="share_id")
    private Share share;

    private Instant createdDate;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TradeType tradeType;

    private Integer quantity;

    public Trade(Portfolio portfolio, Share share, Instant createdDate, Double amount, TradeType tradeType, Integer quantity) {
        this.portfolio = portfolio;
        this.share = share;
        this.createdDate = createdDate;
        this.amount = amount;
        this.tradeType = tradeType;
        this.quantity = quantity;
    }
}
