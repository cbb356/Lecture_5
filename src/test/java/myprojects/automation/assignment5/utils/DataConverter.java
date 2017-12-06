package myprojects.automation.assignment5.utils;

import org.testng.Assert;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConverter {

    private DataConverter() {

    }

    /**
     * Extracts product quantity value from In Stock label displayed on product details page.
     *
     * @param label The content of In Stock label.
     * @return Parsed numeric value.
     */
    public static int parseStockValue(String label) {
        int s = label.length();
        if (s < 8) {
            label = label.substring(0, 1);
        } else {
            label = label.substring(0, s - 7);
        }
        return Integer.valueOf(label);

    }

    /**
     * Extracts price value from price labels.
     *
     * @return Parsed float value of the price.
     */
    public static float parsePriceValue(String label) {
        label = label.replace(',','.');
        int s = label.length();
        return Float.valueOf(label.substring(0, s-2));

    }

    /**
     * Converts float price value to string representation.
     *
     * @param value
     * @return
     */
    public static String convertPriceValue(float value) {
        DecimalFormatSymbols separators = new DecimalFormatSymbols();
        separators.setDecimalSeparator(',');
        return new DecimalFormat("#0.00", separators).format(value);
    }
}
