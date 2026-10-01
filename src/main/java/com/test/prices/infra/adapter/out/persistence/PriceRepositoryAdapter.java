package com.test.prices.infra.adapter.out.persistence;

import com.test.prices.domain.model.Price;
import com.test.prices.application.port.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class PriceRepositoryAdapter implements PriceRepository {

    private final SpringDataPriceRepository repository;

    public PriceRepositoryAdapter(SpringDataPriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Price> findApplicablePrice(
            Long brandId,
            Long productId,
            LocalDateTime applicationDate
    ) {
        return repository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                    brandId,
                    productId,
                    applicationDate,
                    applicationDate)
                .map(this::toDomain);
    }

    private Price toDomain(PriceEntity entity) {
        return new Price(
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getProductId(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }
}
