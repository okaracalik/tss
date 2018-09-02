/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 *
 * @author okaracalik
 */
public class DateTimeUtil {

    public static LocalDate convertDateToLocalDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date convertLocalDateToDate(LocalDate d) {
        return java.sql.Date.valueOf(d);
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

}
