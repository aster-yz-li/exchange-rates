/* Author: Aster Li
 * This class conveniently models dates and provides basic features (easy printing, date comparison, "equals" method)
 * for the purposes of this program.
 */
public class Date {
    public final int YEAR;
    public final int MONTH;
    public final int DAY;

    //Constructs a Date object given int values for year, month, and day.
    public Date(int year, int month, int day) {
        YEAR = year;
        MONTH = month;
        DAY = day;
    }

    //Overrides the "equals" method in Object; Date objects are equal if they have same year, month, and day
    public boolean equals(Object date) {
        if (!this.getClass().equals(date.getClass())) {
            return false;
        }
        else {
            return YEAR == ((Date) date).YEAR && MONTH == ((Date) date).MONTH && DAY == ((Date) date).DAY;
        }
    }

    //Overrides the "hashCode" method in Object; gives a hash code based on the date the object represents.
    public int hashCode() {
        return Integer.parseInt("" + YEAR + MONTH + DAY);
    }

    //Returns the date in ISO format (YYYY-MM-DD).
    public String toString() {
        String date = YEAR + "-";
        if (MONTH < 10) {
            date += "0";
        }
        date += MONTH + "-";
        if (DAY < 10) {
            date += "0";
        }
        return date + DAY;
    }

    //Compares two Dates; if the first date is later, returns an int > 0 and vice versa; returns 0 if dates are equal
    public static int compare(Date date1, Date date2) {
        if (date1.YEAR != date2.YEAR) {
            return Integer.compare(date1.YEAR, date2.YEAR);
        }
        else if (date1.MONTH != date2.MONTH) {
            return Integer.compare(date1.MONTH, date2.MONTH);
        }
        else {
            return Integer.compare(date1.DAY, date2.DAY);
        }
    }
}
