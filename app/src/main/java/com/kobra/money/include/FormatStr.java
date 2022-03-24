package com.kobra.money.include;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;

public class FormatStr {
    public static String getMonthName(int month) {
        HashMap<Integer, String> monthName = new HashMap() {{
            put(1, "январь");
            put(2, "февраль");
            put(3, "март");
            put(4, "апрель");
            put(5, "май");
            put(6, "июнь");
            put(7, "июль");
            put(8, "август");
            put(9, "сентябрь");
            put(10, "октябрь");
            put(11, "ноябрь");
            put(12, "декабрь");
        }};

        return (month > 0 && month <= 12) ? monthName.get(month) : "";
    }

    public static String getMonthGenitiveName(int month) {
        HashMap<Integer, String> monthName = new HashMap() {{
            put(1, "января");
            put(2, "февраля");
            put(3, "марта");
            put(4, "апреля");
            put(5, "мая");
            put(6, "июня");
            put(7, "июля");
            put(8, "августа");
            put(9, "сентября");
            put(10, "октября");
            put(11, "ноября");
            put(12, "декабря");
        }};

        return (month > 0 && month <= 12) ? monthName.get(month) : "";
    }

    public static String getDate(LocalDate date) {
        return date.getDayOfMonth() + " " + getMonthGenitiveName(date.getMonthValue()) + " " + date.getYear();
    }

    public static String getAmount(int amount) {
        return NumberFormat.getNumberInstance(Locale.US).format(amount).replace(",", " ") + " ₽";
    }
}
