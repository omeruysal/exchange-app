package com.eva.exchange.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
public class SharePrice {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="share_id")
    private Share share;

    private Double price;

    private Instant createdDate;

    public SharePrice(Share share, Double price, Instant createdDate) {
        this.share = share;
        this.price = price;
        this.createdDate = createdDate;
    }
}
