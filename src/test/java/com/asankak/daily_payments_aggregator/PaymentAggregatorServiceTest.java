package com.asankak.daily_payments_aggregator;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.asankak.daily_payments_aggregator.service.PaymentAggregatorService;

@SpringBootTest
@ActiveProfiles("test-aud-eur")
public class PaymentAggregatorServiceTest {

	@Autowired
	private PaymentAggregatorService service;

	@Test
	public void test_getLowestEurValue_returns_converted_value() {
		Assertions.assertTrue(service.getLowestEurValue().compareTo(BigDecimal.valueOf(20.0)) < 0);
	}

	@Test
	public void test_getTransactionVolumeInEur_returns_converted_value() {
		Assertions.assertTrue(service.getTransactionVolumeInEur().compareTo(BigDecimal.valueOf(2520.0)) < 0);
	}

	@Test
	public void test_getOutstandingAmountsPerCurrency_has_multiple_currencies() {
		Assertions.assertEquals(service.getOutstandingAmountsPerCurrency().size(), 2);
	}

}
