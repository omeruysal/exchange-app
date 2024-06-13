package com.eva.exchange.repository;

import com.eva.exchange.entity.SharePrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SharePriceRepository extends JpaRepository<SharePrice, Long> {

    Optional<SharePrice> findFirstByShare_IdOrderByIdDesc(Long shareId);

}
