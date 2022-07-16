package com.example.orderdemo.util;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class LocalDatetimehelper {
    public static LocalDateTime generateLocalDate() {
        Faker faker = new Faker();
        long minDay = LocalDate.of(2022, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2022, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate localDate = LocalDate.ofEpochDay(randomDay);
        LocalTime localTime = LocalTime.of(faker.number().numberBetween(1,12), faker.number().numberBetween(1,60));
        return LocalDateTime.of(localDate, localTime);
    }
}
