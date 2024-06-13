package com.eva.exchange.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Version;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Share {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private String name;

    private Double rate;

    private int remainingCount;

    @OneToMany(mappedBy = "share", cascade = CascadeType.ALL)
    private List<SharePrice> sharePrices;

    @Version // Adding this "@Version" annotation and "version" field activate JPA Optimistic Approach.
    // Optimistic Approach helps us to manage concurrent operations
    private Integer version;

    public Share(String symbol, String name, Double rate, int remainingCount) {
        this.symbol = symbol;
        this.name = name;
        this.rate = rate;
        this.remainingCount = remainingCount;
    }
}
