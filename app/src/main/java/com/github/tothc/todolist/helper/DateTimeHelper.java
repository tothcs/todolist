package com.github.tothc.todolist.helper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.Date;

public class DateTimeHelper {

    public static DateTimeFormatter getFormatter() {
        return new DateTimeFormatterBuilder().appendYear(2, 2).appendLiteral(". ")
                .appendMonthOfYear(2).appendLiteral(". ").appendDayOfMonth(2).appendLiteral(". ")
                .appendHourOfDay(2).appendLiteral(":").appendMinuteOfHour(2)
                .appendLiteral(" (").appendDayOfWeekShortText().appendLiteral(")").toFormatter();
    }

    public static DateTime createDateTime(Date date) {
        return new DateTime(date).plusMonths(1);
    }
}
