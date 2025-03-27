package com.asankak.daily_payments_aggregator.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.asankak.daily_payments_aggregator.config.AppConfig;
import com.asankak.daily_payments_aggregator.model.CurrencyRate;
import com.asankak.daily_payments_aggregator.model.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileExtractorServiceImpl implements FileExtractorService {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private final AppConfig config;

	private List<CurrencyRate> currencyRates;
	private List<Payment> payments;
	private final ResourceLoader resourceLoader;

	@Override
	public List<CurrencyRate> extractCurrencyRates() {
		if (currencyRates == null) {
			currencyRates = new ArrayList<>();

			Resource resource = resourceLoader.getResource(config.getCurrencyRateFilePath());

			try (

					InputStream inputStream = resource.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

					Stream<String> lines = reader.lines()) {

				lines.forEach(line -> {

					CurrencyRate currencyRate = processCurrencyLine(line);
					currencyRates.add(currencyRate);
				});
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		return currencyRates;
	}

	@Override
	public List<Payment> extractPayments() {
		if (payments == null) {
			payments = new ArrayList<>();

			Resource resource = resourceLoader.getResource(config.getPaymentFilePath());

			try (InputStream inputStream = resource.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

					Stream<String> lines = reader.lines()) {
				lines.forEach(line -> {

					Payment payment = processPaymentLine(line);
					payments.add(payment);
				});
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		return payments;
	}

	private CurrencyRate processCurrencyLine(String line) {
		String arr[] = line.split(";");
		CurrencyRate currencyRate = new CurrencyRate();
		currencyRate.setTimestamp(LocalDateTime.parse(arr[0], FORMATTER));
		currencyRate.setBaseCurrency(Currency.getInstance(arr[1]));
		currencyRate.setForeignCurrency(Currency.getInstance(arr[2]));
		currencyRate.setRate(Double.valueOf(arr[3]));
		log.info(currencyRate.toString());
		return currencyRate;

	}

	private Payment processPaymentLine(String line) {
		String arr[] = line.split(";");
		Payment payment = new Payment();
		payment.setTimestamp(LocalDateTime.parse(arr[0], FORMATTER));
		payment.setCompany(arr[1]);
		payment.setCurrency(Currency.getInstance(arr[2]));
		payment.setAmount(Double.valueOf(arr[3]));
		log.info(payment.toString());
		return payment;
	}

}
