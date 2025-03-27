package com.asankak.daily_payments_aggregator.config;

import java.io.IOException;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ConfigurationProperties(prefix = "app")
@ConfigurationPropertiesScan
@Configuration
@Data
@Slf4j
@RequiredArgsConstructor
public class AppConfig {

	String paymentFilePath;
	String currencyRateFilePath;

	@PostConstruct
	void logInitValues() throws IOException {
		log.info("Payment file path {}", paymentFilePath);
		log.info("Currency file path {}", currencyRateFilePath);
	}

}
