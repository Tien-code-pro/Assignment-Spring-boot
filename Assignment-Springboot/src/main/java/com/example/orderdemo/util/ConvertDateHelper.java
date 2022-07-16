package com.example.orderdemo.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConvertDateHelper {
    private static final DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime convertStringToLocalDateTime(String date){
        return LocalDate.parse(date, formatter).atStartOfDay();
    }

    public static String convertLocalDateTimeToString(LocalDateTime date){
        return date.format(formatter);
    }
}
