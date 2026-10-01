package com.test.prices.application;

import com.test.prices.domain.model.Price;
import com.test.prices.application.port.PriceRepository;
import com.test.prices.application.port.in.GetApplicablePriceUseCase;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetApplicablePriceServiceTest {

    private final PriceRepository priceRepository = mock(PriceRepository.class);
    private final GetApplicablePriceUseCase useCase =
            new GetApplicablePriceService(priceRepository);

    @Test
    void shouldReturnApplicablePrice() {
        final LocalDateTime applicationDate = LocalDateTime.of(
                2020, 6, 14, 16, 0
        );

        final Price price = new Price(
                1L,
                LocalDateTime.of(2020, 6, 14, 15, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                2L,
                35455L,
                1,
                new BigDecimal("25.45"),
                "EUR"
        );

        when(priceRepository.findApplicablePrice(
                1L,
                35455L,
                applicationDate
        )).thenReturn(Optional.of(price));

        final Optional<Price> result = useCase.execute(
                1L,
                35455L,
                applicationDate
        );

        assertTrue(result.isPresent());
        assertEquals(price, result.get());

        verify(priceRepository).findApplicablePrice(
                1L,
                35455L,
                applicationDate
        );
    }

    @Test
    void shouldReturnEmptyWhenNoApplicablePriceExists() {
        final LocalDateTime applicationDate = LocalDateTime.of(
                2021, 1, 1, 10, 0
        );

        when(priceRepository.findApplicablePrice(
                1L,
                35455L,
                applicationDate
        )).thenReturn(Optional.empty());

        final Optional<Price> result = useCase.execute(
                1L,
                35455L,
                applicationDate
        );

        assertTrue(result.isEmpty());

        verify(priceRepository).findApplicablePrice(
                1L,
                35455L,
                applicationDate
        );
    }
}
