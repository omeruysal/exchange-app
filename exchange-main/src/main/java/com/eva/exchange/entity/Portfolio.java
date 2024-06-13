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
import org.springframework.data.annotation.Version;

@Entity
@Data
@NoArgsConstructor
public class Portfolio {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Version // Adding this "@Version" annotation and "version" field activate JPA Optimistic Approach.
    // Optimistic Approach helps us to manage concurrent operations
    private Integer version;

    public Portfolio(User user) {
        this.user = user;
    }

}
