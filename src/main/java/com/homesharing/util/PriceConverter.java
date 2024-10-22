package com.homesharing.util;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceConverter {
    public static String convertPriceToVND(long price) {
        if (price < 1000) {
            return String.valueOf(price); // Keep it as is if less than 1000
        }

        if (price < 1000000) {
            return formatWithCommas(price) + " đ"; // Add commas for values less than 1 million
        }

        if (price >= 1000000000) {
            return formatPrice(price / 1_000_000_000.0, "tỷ", price % 1_000_000_000 != 0);
        }

        return formatPrice(price / 1_000_000.0, "triệu", price % 1_000_000 != 0);
    }

    private static String formatWithCommas(long price) {
        return NumberFormat.getInstance(Locale.US).format(price);
    }

    private static String formatPrice(long price, String unit) {
        return price + " " + unit;
    }

    private static String formatPrice(double price, String unit, boolean hasDecimal) {
        if (hasDecimal) {
            return String.format("%.2f %s", price, unit);
        } else {
            return String.format("%.0f %s", price, unit);
        }
    }

    private static String formatPrice(double price, String unit) {
        return String.format("%.1f %s", price, unit);
    }
}