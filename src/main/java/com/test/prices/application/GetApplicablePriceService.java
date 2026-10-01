package com.test.prices.application;

import com.test.prices.application.port.PriceRepository;
import com.test.prices.application.port.in.GetApplicablePriceUseCase;
import com.test.prices.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    private final PriceRepository priceRepository;

    public GetApplicablePriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
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