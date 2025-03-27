package com.asankak.daily_payments_aggregator.service.dto;

import java.math.BigDecimal;
import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Summary {

	BigDecimal highestEurValue;
	BigDecimal lowestEurValue;
	Map<String, BigDecimal> outstandingAmountsPerCompanyinEur;
	BigDecimal transactionVolumeInEur;
	Map<String, BigDecimal> outstandingAmountsPerCurrency;

}
