package com.asankak.daily_payments_aggregator.startup;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.asankak.daily_payments_aggregator.service.FileExtractorService;
import com.asankak.daily_payments_aggregator.service.PaymentAggregatorService;
import com.asankak.daily_payments_aggregator.service.dto.Summary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

	private final FileExtractorService fileService;
	private final PaymentAggregatorService paymentService;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		fileService.extractCurrencyRates();
		fileService.extractPayments();

		Summary summary = paymentService.getDailySummary();

		log.info("Highest EUR value (for a single payment) {}",
				(summary.getHighestEurValue() == null ? "N.A" : summary.getHighestEurValue()));
		log.info("Lowest EUR value (for a single payment) {}",
				summary.getLowestEurValue() == null ? "N.A" : summary.getLowestEurValue());
		log.info("Transaction volume in EUR {}",
				summary.getTransactionVolumeInEur() == null ? "N.A" : summary.getTransactionVolumeInEur());
		log.info("Outstanding amounts per company in EUR...............................");
		summary.getOutstandingAmountsPerCompanyinEur()
				.forEach((key, value) -> log.info("Company {}: Outstanding amount {}", key, value));
		log.info("...............................");
		log.info("Amounts per currency.................................................");
		summary.getOutstandingAmountsPerCurrency()
				.forEach((key, value) -> log.info("Currency {}: Outstanding amount {}", key, value));
		log.info("...............................");

	}

}
