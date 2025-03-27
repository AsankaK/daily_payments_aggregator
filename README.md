# Daily Payment Aggregator 

#### Provides a summary of daily payments based on two files. 
* payment file
 - Defined in the application properties via app.payment-file-path and can be set via the environment variable PAYMENT_FILE_PATH.

* currency file
  - Defined in the application properties via pp.currency-rate-file-path and can be set via the environment variable CURRENCY_FILE_PATH.

When defining the absolute path via environment variables the path must start with "file:". 

Analyzes the payment file and calculates the following:

* Highest EUR value (for a single payment)			
* Lowest EUR value (for a single payment)
* Transaction volume in EUR 
* Outstanding amounts per company in EUR
* Amounts per currency

When EUR conversion rates are not available in the currency file, null values will be returned.


