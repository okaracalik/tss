/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.utils;

import static java.time.DayOfWeek.SUNDAY;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import static java.time.temporal.TemporalAdjusters.next;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import jee18.entities.enums.TimesheetFrequency;

/**
 *
 * @author okaracalik
 */
public class DateTimeUtil {

    public static LocalDate convertDateToLocalDate(Date d) {
        if (d != null) {
            return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        else {
            return null;
        }
    }

    public static Date convertLocalDateToDate(LocalDate d) {
        if (d != null) {
            return java.util.Date.from(d.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant());
        }
        else {
            return null;
        }
    }

    public static LocalTime convertDateToLocalTime(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static Date convertLocalTimeToDate(LocalTime d, LocalDate l) {
        return Date.from(d.atDate(l).atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate setToFirstDayOfMonth(LocalDate d) {
        return d.with(TemporalAdjusters.firstDayOfMonth());
    }

    public static LocalDate setToLastDayOfMonth(LocalDate d) {
        return d.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static Integer calculateDurationOfContract(LocalDate start, LocalDate end) {
        Period p = Period.between(start, end);
        return p.getYears() * 12 + p.getMonths() + 1;
    }

    public static HashMap<LocalDate, LocalDate> findTimesheetDates(LocalDate start, LocalDate end, TimesheetFrequency frequency) {
//        System.out.print(start.toString() + " " + end.toString() + " " + frequency);
        HashMap<LocalDate, LocalDate> dates = new HashMap<>();
        LocalDate tempStart = start;
        if (frequency == TimesheetFrequency.MONTHLY) {
            do {
                LocalDate tempEnd = setToLastDayOfMonth(tempStart);
                dates.put(tempStart, tempEnd);
                tempStart = tempEnd.plusDays(1);
//                System.out.println("jee18.utils.DateTimeUtil.findTimesheetDates(): " + tempStart + " " + tempEnd);
            }
            while (!tempStart.isAfter(end));
        }
        else {
            // if start is sunday
            // if end is after end
            LocalDate tempEnd;

            if (tempStart.getDayOfWeek() == SUNDAY) {
                tempEnd = tempStart;
                dates.put(tempStart, tempEnd);
//                System.out.println("jee18.utils.DateTimeUtil.findTimesheetDates(): " + tempStart + " " + tempEnd);
                tempStart = tempEnd.plusDays(1);
            }

            do {
                tempEnd = tempStart.with(next(SUNDAY));
                if (tempEnd.isAfter(end)) {
                    tempEnd = setToLastDayOfMonth(tempStart);
                }
                dates.put(tempStart, tempEnd);
//                System.out.println("jee18.utils.DateTimeUtil.findTimesheetDates(): " + tempStart + " " + tempEnd);
                tempStart = tempEnd.plusDays(1);
            }
            while (!tempStart.isAfter(end));

        }
        return dates;
    }

    private long calculateNonWorkingDaysBetweenDates(Date startDate, Date endDate) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(startDate);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDate);

        int sundays = 0;
        int saturday = 0;

        while (!c1.after(c2)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                saturday++;
            }
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                sundays++;
            }

            c1.add(Calendar.DATE, 1);
        }

        System.out.println("Saturday Count = " + saturday);
        System.out.println("Sunday Count = " + sundays);
        return saturday + sundays;
    }

}
