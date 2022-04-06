package com.example.mypets.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Date {

    private static final String[] FORMATS = new String[]{
            "dd.MM.yyyy", "d.MM.yyyy", "dd.M.yyyy", "yyyy-MM-dd"
    };

    public static LocalDate parseDate(String textDate) throws IllegalArgumentException {
        if (textDate == null || textDate.isEmpty()) {
            return null;
        }

        LocalDate date;

        for (String format : FORMATS) {
            try {
                date = LocalDate.parse(textDate, DateTimeFormatter.ofPattern(format));
                return date;
            } catch (Exception ignored) {
            }
        }

        throw new IllegalArgumentException("Invalid date format");
    }
}
