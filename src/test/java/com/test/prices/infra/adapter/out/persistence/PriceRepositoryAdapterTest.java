package com.test.prices.infra.adapter.out.persistence;

import com.test.prices.application.port.PriceRepository;
import com.test.prices.domain.model.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PriceRepositoryAdapterTest {

    private final SpringDataPriceRepository repository =
            mock(SpringDataPriceRepository.class);

    private final PriceRepository priceRepository =
            new PriceRepositoryAdapter(repository);

    @Test
    void shouldReturnMappedPriceWhenApplicablePriceExists() {
        final LocalDateTime applicationDate = LocalDateTime.of(
                2020, 6, 14, 16, 0
        );

        final PriceEntity entity = new PriceEntity(
                1L,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2L,
                35455L,
                1,
                new BigDecimal("25.45"),
                "EUR"
        );

        when(repository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        1L,
                        35455L,
                        applicationDate,
                        applicationDate
                ))
                .thenReturn(Optional.of(entity));

        final Optional<Price> result = priceRepository.findApplicablePrice(
                1L,
                35455L,
                applicationDate
        );

        assertTrue(result.isPresent());

        final Price price = result.get();

        assertEquals(1L, price.brandId());
        assertEquals(35455L, price.productId());
        assertEquals(2L, price.priceList());
        assertEquals(
                LocalDateTime.of(2020, 6, 14, 15, 0),
                price.startDate()
        );
        assertEquals(
                LocalDateTime.of(2020, 6, 14, 18, 30),
                price.endDate()
        );
        assertEquals(1, price.priority());
        assertEquals(new BigDecimal("25.45"), price.price());
        assertEquals("EUR", price.currency());

        verify(repository)
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        1L,
                        35455L,
                        applicationDate,
                        applicationDate
                );
    }

    @Test
    void shouldReturnEmptyWhenNoApplicablePriceExists() {
        final LocalDateTime applicationDate = LocalDateTime.of(
                2021, 1, 1, 10, 0
        );

        when(repository
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        1L,
                        35455L,
                        applicationDate,
                        applicationDate
                ))
                .thenReturn(Optional.empty());

        final Optional<Price> result = priceRepository.findApplicablePrice(
                1L,
                35455L,
                applicationDate
        );

        assertTrue(result.isEmpty());

        verify(repository)
                .findFirstByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        1L,
                        35455L,
                        applicationDate,
                        applicationDate
                );
    }
}