import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Scanner;

/* Author: Aster Li
 * This class performs most of the program's data processing and computation. It loads date, country, and exchange rate
 * information from the CSV file, performs necessary calculations, and (when the Client class calls certain methods in
 * this class) outputs statistics for the user to view.
 */
public class Compute {
    private static LinkedHashMap<String, Currency> currencies = new LinkedHashMap<>();
    private static Currency base = null;
    private static Currency quote = null;
    private static Date beginDate = null;
    private static Date endDate = null;
    private static LinkedHashMap<Date, Double> rates = new LinkedHashMap<>();

    //This method loads data from the CSV file and also, for future calculations, stores U.S. Dollar to U.S. Dollar
    //exchange rates as 1.0 for all relevant dates (a currency quoted against itself has an exchange rate of 1).
    public static void loadData() throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File("daily.csv"));
        String country = null;
        Date startDate = null;
        Date lastStoredDate = null;
        LinkedHashMap<Date, Double> r = new LinkedHashMap<>();
        while (fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            ArrayList<String> list = new ArrayList<>(Arrays.asList(line.split(",")));
            String dateString = list.get(0);
            String thisCountry = list.get(1);
            if (Currency.COUNTRIES.contains(thisCountry)) {
                if (country == null) {
                    country = thisCountry;
                }
                else if (!country.equals(thisCountry)) {
                    currencies.put(country, new Currency(country, startDate, lastStoredDate, r));
                    country = thisCountry;
                    startDate = null;
                    lastStoredDate = null;
                    r = new LinkedHashMap<>();
                }
                if (list.size() > 2) {
                    int year = Integer.parseInt(dateString.substring(0, 4));
                    int month = Integer.parseInt(dateString.substring(5, 7));
                    int day = Integer.parseInt(dateString.substring(8));
                    Date date = new Date(year, month, day);
                    if (startDate == null) {
                        startDate = date;
                    }
                    lastStoredDate = date;
                    r.put(date, Double.parseDouble(list.get(2)));
                }
            }
        }
        if (country != null) {
            currencies.put(country, new Currency(country, startDate, lastStoredDate, r));
        }
        LinkedHashMap<Date, Double> USD = new LinkedHashMap<>();
        for (int year = 1971; year <= 2018; year++) {
            for (int month = 1; month <= 12; month++) {
                if (month == 2) {
                    if (year % 4 == 0) {
                        for (int day = 1; day <= 29; day++) {
                            USD.put(new Date(year, month, day), 1.0);
                        }
                    }
                    else {
                        for (int day = 1; day <= 28; day++) {
                            USD.put(new Date(year, month, day), 1.0);
                        }
                    }
                }
                else if (month == 4 || month == 6 || month == 9 || month == 11) {
                    for (int day = 1; day <= 30; day++) {
                        USD.put(new Date(year, month, day), 1.0);
                    }
                }
                else {
                    for (int day = 1; day <= 31; day++) {
                        USD.put(new Date(year, month, day), 1.0);
                    }
                }
            }
        }
        currencies.put("United States", new Currency("United States", new Date(1971, 1, 1),
                new Date(2018, 12, 31), USD));
    }

    //This method takes two country names and generates exchange rates for the pair (not all pairs will necessarily
    //include the U.S. Dollar, the base of all rates in the CSV file, so this method is needed).
    public static void loadPair(String baseCountry, String quoteCountry) {
        base = currencies.get(baseCountry);
        quote = currencies.get(quoteCountry);
        Date lastStoredDate = null;
        for (Date date : base.RATES.keySet()) {
            if (quote.RATES.containsKey(date)) {
                rates.put(date, quote.RATES.get(date) / base.RATES.get(date));
                lastStoredDate = date;
                if (beginDate == null) {
                    beginDate = date;
                }
            }
        }
        endDate = lastStoredDate;
    }

    //This method retrieves and outputs data when the user asks for data for a single day.
    public static void output1(Date date) {
        Date prevDay = null;
        Date nextDay = null;
        ArrayList<Date> dates = new ArrayList<>(rates.keySet());
        if (dates.indexOf(date) != 0) {
            prevDay = dates.get(dates.indexOf(date) - 1);
        }
        if (dates.indexOf(date) != dates.size() - 1) {
            nextDay = dates.get(dates.indexOf(date) + 1);
        }
        System.out.println("\n---------------------------------------------------------------------------------------");
        System.out.println("\nData on " + date.toString() + " for " + base.NAME + " to " + quote.NAME + " (" +
                base.ISO + "/" + quote.ISO + "):");
        System.out.printf("\nExchange Rate: %.4f", rates.get(date));
        if (prevDay != null) {
            System.out.println("\n\nPrevious Trading Day: " + prevDay);
            System.out.printf("Previous Trading Day's Rate: %.4f", rates.get(prevDay));
            double pctChange = (rates.get(date) - rates.get(prevDay)) / rates.get(prevDay) * 100;
            System.out.printf("\nPercent Change from Previous: %.4f", pctChange);
            System.out.print("%");
        }
        if (nextDay != null) {
            System.out.println("\n\nNext Trading Day: " + nextDay);
            System.out.printf("Next Trading Day's Rate: %.4f", rates.get(nextDay));
            double pctChange = (rates.get(nextDay) - rates.get(date)) / rates.get(date) * 100;
            System.out.printf("\nPercent Change to Next: %.4f", pctChange);
            System.out.print("%");
        }
        System.out.println();
        System.out.println("\n---------------------------------------------------------------------------------------");
        System.out.println("\nIf you would like other data or statistics, please re-run the program. Thank you!");
    }

    //This method retrieves data and calculates statistics, and then prints them, when the user asks for data and
    //statistics for the period between two dates.
    public static void output2(Date date1, Date date2) {
        System.out.println("\n---------------------------------------------------------------------------------------");
        System.out.println("\nData from " + date1 + " to " + date2 + " for " + base.NAME + " to " + quote.NAME + " (" +
                base.ISO + "/" + quote.ISO + "):");
        System.out.printf("\nExchange Rate at Start of Period: %.4f", rates.get(date1));
        System.out.printf("\nExchange Rate at End of Period: %.4f", rates.get(date2));
        System.out.printf("\nRate Change from Start to End: %.4f", rates.get(date2) - rates.get(date1));
        System.out.printf(" (%.4f", (rates.get(date2) - rates.get(date1)) / rates.get(date1) * 100);
        System.out.println("%)");

        ArrayList<Date> allDates = new ArrayList<>(rates.keySet());
        ArrayList<Date> dateList = new ArrayList<>(allDates.subList(allDates.indexOf(date1),
                allDates.indexOf(date2) + 1));
        Date high = null;
        Date low = null;
        double sum = 0;
        for (Date date : dateList) {
            double rate = rates.get(date);
            sum += rate;
            if (high == null) {
                high = date;
                low = date;
            }
            else if (rate > rates.get(high)) {
                high = date;
            }
            else if (rate < rates.get(low)) {
                low = date;
            }
        }

        System.out.printf("\nHighest Exchange Rate: %.4f", rates.get(high));
        System.out.println(" (on " + high + ")");
        System.out.printf("Lowest Exchange Rate: %.4f", rates.get(low));
        System.out.println(" (on " + low + ")");
        System.out.printf("Average Exchange Rate: %.4f", sum / dateList.size());
        System.out.println("\nNumber of Trading Days: " + dateList.size());

        double x = 0;
        for (Date date : dateList) {
            double y = rates.get(date) - (sum / dateList.size());
            x += y * y;
        }
        System.out.printf("Standard Deviation: %.4f", Math.sqrt(x / dateList.size()));
        System.out.println();
        System.out.println("\n---------------------------------------------------------------------------------------");
        System.out.println();
    }

    //This method prints all daily rates for a currency pair within a certain time period.
    public static void allRates(Date date1, Date date2) {
        ArrayList<Date> allDates = new ArrayList<>(rates.keySet());
        ArrayList<Date> dateList = new ArrayList<>(allDates.subList(allDates.indexOf(date1),
                allDates.indexOf(date2) + 1));
        System.out.println("\n---------------------------------------------------------------------------------------");
        System.out.println();
        for (Date date : dateList) {
            System.out.print(date);
            System.out.printf(": %.4f", rates.get(date));
            System.out.println();
        }
        System.out.println("\n---------------------------------------------------------------------------------------");
    }

    //This method outputs an ArrayList of all dates where exchange rate information is available.
    public static ArrayList<Date> getDatesList() {
        return new ArrayList<>(rates.keySet());
    }

    //This method returns true if a date has available exchange rate information, and false if not.
    public static boolean validDate(Date date) {
        if (Date.compare(beginDate, date) > 0 || Date.compare(endDate, date) < 0) {
            throw new IllegalArgumentException("Date Outside of Data Range");
        }
        return rates.containsKey(date);
    }

    //This method returns the first date for which exchange rate data is available (for the stored currency pair).
    public static Date getBeginDate() {
        return beginDate;
    }

    //This method returns the last date for which exchange rate data is available (for the stored currency pair).
    public static Date getEndDate() {
        return endDate;
    }
}
