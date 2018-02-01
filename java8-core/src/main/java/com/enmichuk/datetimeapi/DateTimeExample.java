package com.enmichuk.datetimeapi;

import java.time.*;
import java.util.Date;

public class DateTimeExample {
    public static void main(String[] args) {
        Date oldDate = new Date();

        /*
        These classes are mainly used when timezone are not required to be explicitly specified in the context.
        It's used when we work with time stamp in general not with specific point in time on the time axis.
        LocalDate can be used to store dates like birthdays and paydays.
        The LocalTime represents time without a date.
        The LocalDateTime is used to represent a combination of date and time.
         */
        LocalDateTime newLocalDateTime = LocalDateTime.now();
        LocalDate newLocalDate = newLocalDateTime.toLocalDate();
        LocalTime newLocalTime = newLocalDateTime.toLocalTime();
        LocalDateTime localDateTimeFromOldDate = LocalDateTime.ofInstant(oldDate.toInstant(), ZoneId.systemDefault());

        /*
        ZonedDateTime when we need to deal with time zone specific date and time. The ZoneId is an identifier used to represent different zones.
        There are about 40 different time zones and the ZoneId are used to represent them as follows.
         */
        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
        ZonedDateTime newZonedDateTime = ZonedDateTime.of(newLocalDateTime, parisZoneId);

        System.out.println("Old Date: " + oldDate);
        System.out.println("New LocalDateTime: " + newLocalDateTime);
        System.out.println("LocalDateTime from old Date: " + localDateTimeFromOldDate);
        System.out.println("New ZonedDataTime: " + newZonedDateTime);
        System.out.println();
        System.out.println("Old current time millis in UTC:  " + oldDate.getTime());
        System.out.println("New current time millis in UTC:  " + newLocalDateTime.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
        System.out.println("New current time seconds in UTC: " + newLocalDateTime.toEpochSecond(OffsetDateTime.now().getOffset()));
        System.out.println("New current time seconds in UTC: " + newLocalDateTime.atZone(ZoneId.systemDefault()).toEpochSecond());
        System.out.println("New current time seconds in Europe/Paris time zone: " + newZonedDateTime.toInstant().toEpochMilli());
        System.out.println();


        /*
        The Period class represents a quantity of time in terms of years, months and days and
        the Duration class represents a quantity of time in terms of seconds and nano seconds.
         */
        LocalDate finalDate = newLocalDate.plus(Period.ofDays(5));
        System.out.println("New LocalDate: " + newLocalDate);
        System.out.println("LocalDate plus 5 days: " + finalDate);
        LocalTime finalTime = newLocalTime.plus(Duration.ofSeconds(30));
        System.out.println("New LocalTime: " + newLocalTime);
        System.out.println("LocalTime plus 30 seconds: " + finalTime);



    }
}
