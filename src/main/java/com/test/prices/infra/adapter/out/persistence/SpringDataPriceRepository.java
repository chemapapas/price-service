package com.test.prices.infra.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SpringDataPriceRepository extends JpaRepository<PriceEntity, Long> {

    Optional<PriceEntity>
    findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
            Long brandId,
            Long productId,
            LocalDateTime startDate,
            LocalDateTime endDate
    );
}
