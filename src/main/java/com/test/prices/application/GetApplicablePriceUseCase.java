package com.test.prices.application;

import com.test.prices.domain.model.Price;
import com.test.prices.domain.port.PriceRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class GetApplicablePriceUseCase {

    private final PriceRepository priceRepository;

    public GetApplicablePriceUseCase(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Optional<Price> execute(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate
    ) {
        return priceRepository.findApplicablePrice(
                brandId,
                productId,
                applicationDate
        );
    }
}