import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/* Author: Aster Li
 * This is the client class for the program. The class takes user input, processes it (throwing exceptions when it
 * detects anomalies), and calls methods from the Compute class in order to output to the user any requested data.
 */
public class Client {
    //All code of this class is in the main method, which performs the functions described above.
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("\nLoading, please wait...");
        Compute.loadData();
        System.out.println("\nThank you for using this program. If you have not read the README.txt file, " +
                "you may wish to do so before continuing.");
        System.out.println("This program allows you to access exchange rate data and statistics for a currency pair " +
                "at either a specific date or for the period between two dates.");
        System.out.println("\nThe currencies of these countries are supported:\n");
        for (String country : Currency.COUNTRIES) {
            System.out.println(country + " - " + Currency.countryToName(country) + " (" +
                    Currency.countryToISO(country) + ")");
        }

        Scanner in = new Scanner(System.in);
        System.out.print("\nPlease input the country (name as written above) of your desired base currency " +
                "(for Euros, input \"Euro\"): ");
        String base = in.nextLine();
        if (!Currency.COUNTRIES.contains(base)) {
            throw new IllegalArgumentException("Unsupported Country or Unrecognized Country Name");
        }
        System.out.print("Please input the country of your desired quote currency: ");
        String quote = in.nextLine();
        if (!Currency.COUNTRIES.contains(quote)) {
            throw new IllegalArgumentException("Unsupported Country or Unrecognized Country Name");
        }

        Compute.loadPair(base, quote);
        System.out.println("\nData for this pair is available from " + Compute.getBeginDate().toString() + " to " +
                Compute.getEndDate().toString());
        System.out.print("Would you like data for one day or data and statistics for a time period? " +
                "Please input '1' for the former or '2' for the latter: ");
        int choice = in.nextInt();
        if (choice != 1 && choice != 2) {
            throw new IllegalArgumentException("Please input either 1 or 2.");
        }

        if (choice == 1) {
            boolean done = false;
            Date date = null;
            while (!done) {
                System.out.print("\nPlease input the date in ISO format (YYYY-MM-DD): ");
                String dateString = in.next();
                int year = Integer.parseInt(dateString.substring(0, 4));
                int month = Integer.parseInt(dateString.substring(5, 7));
                int day = Integer.parseInt(dateString.substring(8));
                date = new Date(year, month, day);
                if (!Compute.validDate(date)) {
                    ArrayList<Date> dates = Compute.getDatesList();
                    Date before = null;
                    Date after = null;
                    for (int i = 0; i < dates.size(); i++) {
                        if (Date.compare(date, dates.get(i)) < 0) {
                            after = dates.get(i);
                            before = dates.get(i - 1);
                            break;
                        }
                    }
                    System.out.println("\nSorry, there is no data for that date (the market is closed on weekends, " +
                            "and some other gaps (less frequent) exist in the data set).");
                    System.out.println("Please try another date; the closest available dates before and after are " +
                            before + " and " + after + ", respectively.");
                }
                else {
                    done = true;
                }
            }
            Compute.output1(date);
        }

        if (choice == 2) {
            boolean done = false;
            Date startDate = null;
            while (!done) {
                System.out.print("\nPlease input the start date (inclusive) in ISO format (YYYY-MM-DD): ");
                String dateString = in.next();
                int year = Integer.parseInt(dateString.substring(0, 4));
                int month = Integer.parseInt(dateString.substring(5, 7));
                int day = Integer.parseInt(dateString.substring(8));
                startDate = new Date(year, month, day);
                if (!Compute.validDate(startDate)) {
                    ArrayList<Date> dates = Compute.getDatesList();
                    Date before = null;
                    Date after = null;
                    for (int i = 0; i < dates.size(); i++) {
                        if (Date.compare(startDate, dates.get(i)) < 0) {
                            after = dates.get(i);
                            before = dates.get(i - 1);
                            break;
                        }
                    }
                    System.out.println("\nSorry, there is no data for that date (the market is closed on weekends, " +
                            "and some other gaps (less frequent) exist in the data set).");
                    System.out.println("Please try another date; the closest available dates before and after are " +
                            before + " and " + after + ", respectively.");
                }
                else {
                    done = true;
                }
            }
            done = false;
            Date endDate = null;
            while (!done) {
                System.out.print("Please input the end date (inclusive): ");
                String dateString = in.next();
                int year = Integer.parseInt(dateString.substring(0, 4));
                int month = Integer.parseInt(dateString.substring(5, 7));
                int day = Integer.parseInt(dateString.substring(8));
                endDate = new Date(year, month, day);
                if (!Compute.validDate(endDate)) {
                    ArrayList<Date> dates = Compute.getDatesList();
                    Date before = null;
                    Date after = null;
                    for (int i = 0; i < dates.size(); i++) {
                        if (Date.compare(endDate, dates.get(i)) < 0) {
                            after = dates.get(i);
                            before = dates.get(i - 1);
                            break;
                        }
                    }
                    System.out.println("\nSorry, there is no data for that date (the market is closed on weekends, " +
                            "and some other gaps (less frequent) exist in the data set).");
                    System.out.println("Please try another date; the closest available dates before and after are " +
                            before + " and " + after + ", respectively.\n");
                }
                else {
                    done = true;
                }
            }
            if (Date.compare(startDate, endDate) >= 0) {
                throw new IllegalArgumentException("Invalid Date Range");
            }
            Compute.output2(startDate, endDate);
            System.out.print("Would you like to see the full list of daily exchange rates for the period? " +
                    "If so, please input \"Yes\". Input anything else to exit the program: ");
            String x = in.next();
            if (x.equals("Yes")) {
                Compute.allRates(startDate, endDate);
            }
            System.out.println("\nIf you would like other data or statistics, please re-run the program. Thank you!");
        }
    }
}
