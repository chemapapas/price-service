package com.test.prices.application.port;

import com.test.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepository {

    Optional<Price> findApplicablePrice(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate
    );
}
