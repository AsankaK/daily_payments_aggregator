package com.asankak.daily_payments_aggregator.service;

import java.util.List;

import com.asankak.daily_payments_aggregator.model.CurrencyRate;
import com.asankak.daily_payments_aggregator.model.Payment;

public interface FileExtractorService {

	List<CurrencyRate> extractCurrencyRates();

	List<Payment> extractPayments();

}