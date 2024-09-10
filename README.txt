* This was my "Capstone: Advanced Java Topics & Machine Learning" (12th grade) final project. Being much more experienced now,
I am able to optimize a lot of the code but have uploaded the project as-is for the sake of time and authenticity. *

########################################################

Data Methods for the Analysis of Currency Exchange Rates
Author: Aster Li

--------------------------------------------------------

[OVERVIEW]

This program allows the user to access exchange rate data and computed figures, at either a single specified date or for the time between two dates, for any currency pair composed of two currencies from a list of 22 supported ones (the program will list them when it is run). The program will ask for user input, calculate, and output the relevant information.

This program is written in Java 17. Should the in-code file reference to daily.csv need to be modified, it can be done so on line 24 of Compute.java. To run the program, run Client.java.

--------------------------------------------------------

[DATA SOURCE]

Raw data for this program is taken from a GitHub data set: https://github.com/datasets/exchange-rates

This program only uses the "daily" rates CSV file from the above link; it contains exchange rate information (USD/currency) from as far back as 1971 and up to 2018 (some currencies have less data).

(While the data set's description says that most exchange rates are currency/USD and that some are USD/currency, I, having done an inspection of the "daily" file, am quite confident that at least the file I am using contains only USD/currency exchange rates, not the other way around. The program, accordingly, treats all raw exchange rate data as USD/currency.)

For pairs not containing the U.S. dollar, the program calculates the cross rate by using the two currencies' respective exchange rates with the U.S. dollar.

--------------------------------------------------------

[CURRENCY PAIRS] (Standard notation is used, but an explanation is provided here for the user's reference and convenience.)

A currency pair is written ABC/XYZ, where ABC is the base currency's 3-letter ISO code (e.g., "USD" - U.S. Dollar, "EUR" - Euro, "JPY" - Japanese Yen), and XYZ is the quote currency's ISO code.

The exchange rate represents how much of the quote currency one can buy with one unit of the base currency.

For example, a value of 1.2 for EUR/USD means that 1 Euro is worth 1.2 U.S. dollars, and vice versa. (This implies that USD/EUR is at 0.8333, since 1 / 1.2 = 0.8333.)
