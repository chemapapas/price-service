package com.test.prices.application.port.in;

import com.test.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GetApplicablePriceUseCase {

    Optional<Price> execute(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate
    );
}
