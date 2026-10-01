package com.test.prices.infra.adapter.in.rest;

import com.test.prices.application.port.in.GetApplicablePriceUseCase;
import com.test.prices.domain.model.Price;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Validated
@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;

    public PriceController(GetApplicablePriceUseCase getApplicablePriceUseCase) {
        this.getApplicablePriceUseCase = getApplicablePriceUseCase;
    }

    @GetMapping
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @RequestParam(name = "brandId") @Positive Long brandId,
            @RequestParam(name = "productId") @Positive Long productId,
            @RequestParam(name = "applicationDate")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime applicationDate
    ) {
        return getApplicablePriceUseCase
                .execute(brandId, productId, applicationDate)
                .map(this::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private PriceResponse toResponse(Price price) {
        return new PriceResponse(
                price.productId(),
                price.brandId(),
                price.priceList(),
                price.startDate(),
                price.endDate(),
                price.price(),
                price.currency()
        );
    }
}
