package com.asankak.daily_payments_aggregator.model;

import java.time.LocalDateTime;
import java.util.Currency;

import lombok.Data;

@Data
public class Payment {
	
	LocalDateTime timestamp;
	String company;
	Currency currency;
	Double amount;

}
