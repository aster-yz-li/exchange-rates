import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/* Author: Aster Li
 * This class represents a currency (more specifically, a currency pair of USD/currency), containing fields for
 * exchange rates and other information.
 */
public class Currency {
    public final String COUNTRY;
    public final String ISO;
    public final String NAME;
    public final Date BEGIN_DATE;
    public final Date END_DATE;
    public final LinkedHashMap<Date, Double> RATES;

    //This constructor constructs a Currency object given country name, begin date of exchange rate data, end date of
    //exchange rate data, and a LinkedHashMap of exchange rate data.
    public Currency(String country, Date beginDate, Date endDate, LinkedHashMap<Date, Double> rates) {
        COUNTRY = country;
        ISO = countryToISO(country);
        NAME = countryToName(country);
        BEGIN_DATE = beginDate;
        END_DATE = endDate;
        RATES = rates;
    }

    public static final LinkedHashSet<String> COUNTRIES = new LinkedHashSet<>(Arrays.asList("Australia", "Brazil",
            "Canada", "China", "Denmark", "Euro", "Hong Kong", "India", "Japan", "Malaysia", "Mexico", "New Zealand",
            "Norway", "Singapore", "South Africa", "South Korea", "Sweden", "Switzerland", "Taiwan", "Thailand",
            "United Kingdom", "United States"));

    //This method, given a country name, returns the ISO code for that country's currency.
    public static String countryToISO(String country) {
        return switch (country) {
            case "Australia" -> "AUD";
            case "Brazil" -> "BRL";
            case "Canada" -> "CAD";
            case "China" -> "CNY";
            case "Denmark" -> "DKK";
            case "Euro" -> "EUR";
            case "Hong Kong" -> "HKD";
            case "India" -> "INR";
            case "Japan" -> "JPY";
            case "Malaysia" -> "MYR";
            case "Mexico" -> "MXN";
            case "New Zealand" -> "NZD";
            case "Norway" -> "NOK";
            case "Singapore" -> "SGD";
            case "South Africa" -> "ZAR";
            case "South Korea" -> "KRW";
            case "Sweden" -> "SEK";
            case "Switzerland" -> "CHF";
            case "Taiwan" -> "TWD";
            case "Thailand" -> "THB";
            case "United Kingdom" -> "GBP";
            case "United States" -> "USD";
            default -> throw new IllegalArgumentException("Unsupported Country or Unrecognized Country Name");
        };
    }

    //This method, given a country name, returns the currency name for that country's currency.
    public static String countryToName(String country) {
        return switch (country) {
            case "Australia" -> "Australian Dollars";
            case "Brazil" -> "Brazilian Reais";
            case "Canada" -> "Canadian Dollars";
            case "China" -> "Chinese Yuan";
            case "Denmark" -> "Danish Kroner";
            case "Euro" -> "Euros";
            case "Hong Kong" -> "Hong Kong Dollars";
            case "India" -> "Indian Rupees";
            case "Japan" -> "Japanese Yen";
            case "Malaysia" -> "Malaysian Ringgit";
            case "Mexico" -> "Mexican Pesos";
            case "New Zealand" -> "New Zealand Dollars";
            case "Norway" -> "Norwegian Kroner";
            case "Singapore" -> "Singapore Dollars";
            case "South Africa" -> "South African Rand";
            case "South Korea" -> "South Korean Won";
            case "Sweden" -> "Swedish Kronor";
            case "Switzerland" -> "Swiss Francs";
            case "Taiwan" -> "New Taiwan Dollars";
            case "Thailand" -> "Thai Baht";
            case "United Kingdom" -> "British Pounds";
            case "United States" -> "United States Dollars";
            default -> throw new IllegalArgumentException("Unsupported Country or Unrecognized Country Name");
        };
    }
}
