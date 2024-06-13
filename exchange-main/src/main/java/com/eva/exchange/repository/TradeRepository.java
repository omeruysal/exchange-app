package com.eva.exchange.repository;

import com.eva.exchange.entity.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, Long> {

    @Query("SELECT t.tradeType, SUM(t.quantity) FROM Trade t WHERE t.portfolio.user.id = :userId and t.share.id = :shareId GROUP BY t.tradeType ORDER BY t.tradeType")
    List<Object[]> findGroupedTradesByUserIdAndShareId(Long userId, Long shareId);

}
