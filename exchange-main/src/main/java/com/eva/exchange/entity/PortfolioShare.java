package com.eva.exchange.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class PortfolioShare {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="portfolio_id")
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name="share_id")
    private Share share;

    private Integer quantity;

    public PortfolioShare(Portfolio portfolio, Share share, Integer quantity) {
        this.portfolio = portfolio;
        this.share = share;
        this.quantity = quantity;
    }
}
