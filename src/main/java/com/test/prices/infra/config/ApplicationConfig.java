package com.test.prices.infra.config;

import com.test.prices.application.GetApplicablePriceUseCase;
import com.test.prices.domain.port.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(
            PriceRepository priceRepository
    ) {
        return new GetApplicablePriceUseCase(priceRepository);
    }
}
