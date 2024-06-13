package com.eva.exchange.repository;

import com.eva.exchange.entity.PortfolioShare;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioShareRepository extends JpaRepository<PortfolioShare, Long> {
    Optional<PortfolioShare> findByPortfolio_IdAndShare_Id(Long portfolioId, Long shareId);

}
