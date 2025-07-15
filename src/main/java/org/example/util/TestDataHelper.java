package org.example.util;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDataHelper {

    private final static Faker FAKER = Faker.instance();

    public static String getFutureDate( DateTimeFormatter dateFormatter){
        return LocalDate.now()
                .plusDays(FAKER.number().randomNumber(2, true))
                .format(dateFormatter);
    }

    public static Faker getFaker(){
        return FAKER;
    }

    public static int getRandomInt(int numberOfDigits){
        return Math.toIntExact(FAKER.number().randomNumber(numberOfDigits, true));
    }
}
