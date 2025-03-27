package com.asankak.daily_payments_aggregator.service;

import java.math.BigDecimal;
import java.util.Map;

import com.asankak.daily_payments_aggregator.service.dto.Summary;

public interface PaymentAggregatorService {

	BigDecimal getHighestEurValue();

	BigDecimal getLowestEurValue();

	Map<String, BigDecimal> getOutstandingAmountsPerCompanyInEur();

	BigDecimal getTransactionVolumeInEur();

	Map<String, BigDecimal> getOutstandingAmountsPerCurrency();

	Summary getDailySummary();

}
