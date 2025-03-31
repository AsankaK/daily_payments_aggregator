package com.asankak.daily_payments_aggregator.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asankak.daily_payments_aggregator.service.PaymentAggregatorService;
import com.asankak.daily_payments_aggregator.service.dto.Summary;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentAggregatorService service;

	@GetMapping("summary")
	ResponseEntity<Summary> getSummary() {
		return ResponseEntity.ok((service.getDailySummary()));
	}

	@GetMapping("EUR/highest")
	ResponseEntity<BigDecimal> getEurHighest() {
		return ResponseEntity.ok((service.getHighestEurValue()));
	}

	@GetMapping("EUR/lowest")
	ResponseEntity<BigDecimal> getEurLowest() {
		return ResponseEntity.ok((service.getLowestEurValue()));
	}

	@GetMapping("EUR/transaction-volume")
	ResponseEntity<BigDecimal> getEurTransactionVolume() {
		return ResponseEntity.ok((service.getTransactionVolumeInEur()));
	}

	@GetMapping("EUR/outstanding-amounts-per-company")
	ResponseEntity<Map<String, BigDecimal>> getOutstandingAmountsPerCompanyInEur() {
		return ResponseEntity.ok((service.getOutstandingAmountsPerCompanyInEur()));
	}

	@GetMapping("outstanding-amounts-per-currency")
	ResponseEntity<Map<String, BigDecimal>> getOutstandingAmountsPerCurrency() {
		return ResponseEntity.ok((service.getOutstandingAmountsPerCurrency()));
	}

}
