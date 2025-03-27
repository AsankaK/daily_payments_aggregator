package com.asankak.daily_payments_aggregator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.asankak.daily_payments_aggregator.model.CurrencyRate;
import com.asankak.daily_payments_aggregator.model.Payment;
import com.asankak.daily_payments_aggregator.service.dto.Summary;

@Service
public class PaymentAggregatorServiceImpl implements PaymentAggregatorService {

	private static final String EUR = "EUR";

	Map<String, BigDecimal> conversionRates = new HashMap<>();

	private final List<Payment> payments;
	private final List<CurrencyRate> currencyRates;

	public PaymentAggregatorServiceImpl(FileExtractorService fileExtractor) {
		this.payments = fileExtractor.extractPayments();
		this.currencyRates = fileExtractor.extractCurrencyRates();
		init();

	}

	private void init() {
		for (CurrencyRate current : currencyRates) {
			conversionRates.put(
					current.getBaseCurrency().getCurrencyCode() + "To" + current.getForeignCurrency().getCurrencyCode(),
					BigDecimal.valueOf(current.getRate()));
			conversionRates.put(
					current.getForeignCurrency().getCurrencyCode() + "To" + current.getBaseCurrency().getCurrencyCode(),
					BigDecimal.valueOf(1 / current.getRate()));
		}
	}

	private BigDecimal getEurValue(Payment payment) {

		if (EUR.equals(payment.getCurrency().getCurrencyCode())) {
			return BigDecimal.valueOf(payment.getAmount());

		} else {
			String rateKey = payment.getCurrency().getCurrencyCode() + "ToEUR";
			if (conversionRates.containsKey(rateKey)) {
				return conversionRates.get(rateKey).multiply(BigDecimal.valueOf(payment.getAmount())).setScale(4,
						RoundingMode.HALF_EVEN);
			}
			return null;
		}
	}

	@Override
	public BigDecimal getHighestEurValue() {
		BigDecimal highest = null;

		for (Payment payment : payments) {
			BigDecimal eurAmount = getEurValue(payment);

			if (eurAmount != null) {
				if (highest == null || eurAmount.abs().compareTo(highest) > 0) {
					highest = eurAmount.abs();
				}
			}
		}
		return highest;
	}

	@Override
	public BigDecimal getLowestEurValue() {
		BigDecimal lowest = null;

		for (Payment payment : payments) {
			BigDecimal eurAmount = getEurValue(payment);

			if (eurAmount != null) {
				if (lowest == null || eurAmount.abs().compareTo(lowest) < 0) {
					lowest = eurAmount.abs();
				}
			}
		}
		return lowest;
	}

	@Override
	public Map<String, BigDecimal> getOutstandingAmountsPerCompanyInEur() {
		Map<String, BigDecimal> outstandingAmounts = new HashMap<>();

		for (Payment payment : payments) {
			BigDecimal eurAmount = getEurValue(payment);

			if (eurAmount != null) {

				if (outstandingAmounts.containsKey(payment.getCompany())) {
					BigDecimal currentVal = outstandingAmounts.get(payment.getCompany());
					outstandingAmounts.put(payment.getCompany(), currentVal.add(eurAmount));
				} else {
					outstandingAmounts.put(payment.getCompany(), eurAmount);
				}
			}
		}
		return outstandingAmounts;
	}

	@Override
	public BigDecimal getTransactionVolumeInEur() {
		BigDecimal transactionVolume = null;

		for (Payment payment : payments) {
			BigDecimal eurAmount = getEurValue(payment);

			if (eurAmount != null) {

				if (transactionVolume == null) {
					transactionVolume = eurAmount.abs();

				} else {
					transactionVolume = transactionVolume.add(eurAmount.abs());
				}

			}
		}
		return transactionVolume;
	}

	@Override
	public Map<String, BigDecimal> getOutstandingAmountsPerCurrency() {
		Map<String, BigDecimal> outstandingAmounts = new HashMap<>();

		for (Payment payment : payments) {
			if (outstandingAmounts.containsKey(payment.getCurrency().getCurrencyCode())) {
				BigDecimal currentVal = outstandingAmounts.get(payment.getCurrency().getCurrencyCode());
				outstandingAmounts.put(payment.getCurrency().getCurrencyCode(),
						currentVal.add(BigDecimal.valueOf(payment.getAmount())));
			} else {
				outstandingAmounts.put(payment.getCurrency().getCurrencyCode(),
						BigDecimal.valueOf(payment.getAmount()));
			}
		}
		return outstandingAmounts;

	}

	@Override
	public Summary getDailySummary() {
		Summary summary = new Summary();
		summary.setHighestEurValue(getHighestEurValue());
		summary.setLowestEurValue(getLowestEurValue());
		summary.setOutstandingAmountsPerCompanyinEur(getOutstandingAmountsPerCompanyInEur());
		summary.setOutstandingAmountsPerCurrency(getOutstandingAmountsPerCurrency());
		summary.setTransactionVolumeInEur(getTransactionVolumeInEur());

		return summary;
	}

}
