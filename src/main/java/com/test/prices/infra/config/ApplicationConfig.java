package com.test.prices.infra.config;

import com.test.prices.application.GetApplicablePriceService;
import com.test.prices.application.port.PriceRepository;
import com.test.prices.application.port.in.GetApplicablePriceUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(
            PriceRepository priceRepository
    ) {
        return new GetApplicablePriceService(priceRepository);
    }
}
