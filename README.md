# Daily Payment Aggregator 

 Provides a summary of daily payments based on two files. 
* payment file
 - Defined in the application properties via app.payment-file-path and can be set via the environment variable PAYMENT_FILE_PATH.
 
 Format: date+time;company;currency;amount  
 Example: 2025-02-14 10:00:00;Company A;EUR;-1000  
 

* currency file
  - Defined in the application properties via pp.currency-rate-file-path and can be set via the environment variable CURRENCY_FILE_PATH.
  
  Format: date+time;company;currency;amount  
  Example: 2025-03-25 14:10:00;EUR;USD;1.0825  
  

When defining the absolute path via environment variables the path must start with " _file:_ "

Analyzes the payment file and calculates the following:

* Highest EUR value (for a single payment)			
* Lowest EUR value (for a single payment)
* Transaction volume in EUR 
* Outstanding amounts per company in EUR
* Amounts per currency

When EUR conversion rates are not available in the currency file, null values will be returned.


##Payments API

#### The following get methods are provided.
* /api/payment/summary
* /api/payment/EUR/highest
* /api/payment/EUR/lowest
* /api/payment/EUR/transaction-volume
* /api/payment/EUR/outstanding-amounts-per-company
* /api/payment/outstanding-amounts-per-currency