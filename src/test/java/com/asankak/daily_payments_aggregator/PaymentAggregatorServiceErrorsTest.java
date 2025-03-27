package com.asankak.daily_payments_aggregator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.asankak.daily_payments_aggregator.service.PaymentAggregatorService;

@SpringBootTest
@ActiveProfiles("test-no-eur-rates")
public class PaymentAggregatorServiceErrorsTest {

	@Autowired
	private PaymentAggregatorService service;

	@Test
	public void test_getLowestEurValue_returns_converted_value() {
		Assertions.assertNull(service.getLowestEurValue());
	}

	@Test
	public void test_getTransactionVolumeInEur_returns_converted_value() {
		Assertions.assertNull(service.getTransactionVolumeInEur());
	}

	@Test
	public void test_getOutstandingAmountsPerCurrency_has_multiple_currencies() {
		Assertions.assertEquals(service.getOutstandingAmountsPerCurrency().size(), 1);
	}

}
