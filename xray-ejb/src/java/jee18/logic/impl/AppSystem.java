/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee18.dao.ContractAccess;
import jee18.dao.HolidayAccess;
import jee18.dao.PersonAccess;
import jee18.dao.RoleAccess;
import jee18.dao.TimesheetAccess;
import jee18.dao.TimesheetEntryAccess;
import jee18.dto.Holiday;
import jee18.dto.Person;
import jee18.entities.PersonEntity;
import jee18.entities.enums.Day;
import jee18.logic.IAppSystem;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "AppSystem")
public class AppSystem implements IAppSystem {

    @EJB
    private HolidayAccess holidayAccess;

    @EJB
    private RoleAccess roleAccess;

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private TimesheetAccess timesheetAccess;

    @EJB
    private TimesheetEntryAccess timesheetEntryAccess;

    @EJB
    private PersonAccess personAccess;

    @Override
    public void generateData() {
        PersonEntity temp;
        String secretary = "SECRETARY";
        String supervisor = "SUPERVISOR";
        String employee = "EMPLOYEE";
        String assistant = "ASSISTANT";

        for (int i = 1; i < 4; i++) {
            try {
                Person se = new Person();
                se.setFirstName(secretary.toLowerCase() + "-" + i);
                se.setLastName("a");
                se.setEmailAddress(se.getFirstName() + "@" + se.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(se), Stream.of(secretary).collect(Collectors.toList()));

                Person su = new Person();
                su.setFirstName(supervisor.toLowerCase() + "-" + i);
                su.setLastName("a");
                su.setEmailAddress(su.getFirstName() + "@" + su.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(su), Stream.of(supervisor).collect(Collectors.toList()));

                Person em = new Person();
                em.setFirstName(employee.toLowerCase() + "-" + i);
                em.setLastName("a");
                em.setEmailAddress(em.getFirstName() + "@" + em.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(em), Stream.of(employee).collect(Collectors.toList()));

                Person as = new Person();
                as.setFirstName(assistant.toLowerCase() + "-" + i);
                as.setLastName("a");
                as.setEmailAddress(as.getFirstName() + "@" + as.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(as), Stream.of(assistant).collect(Collectors.toList()));
            }
            catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AppSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            List<Holiday> holidays = new ArrayList();
            holidays.add(new Holiday("New Year´s Day", formatter.parse("2018-01-01"), Day.MONDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2018-03-30"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2018-04-02"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2018-05-01"), Day.TUESDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2018-05-10"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2018-05-21"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2018-05-31"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2018-10-03"), Day.WEDNESDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2018-11-01"), Day.THURSDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2018-12-25"), Day.TUESDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2018-12-26"), Day.WEDNESDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2019-01-01"), Day.TUESDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2019-04-19"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2019-04-22"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2019-05-01"), Day.WEDNESDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2019-05-20"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2019-06-10"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2019-06-20"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2019-10-03"), Day.THURSDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2019-11-01"), Day.FRIDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2019-12-25"), Day.WEDNESDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2019-12-26"), Day.THURSDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2020-01-01"), Day.TUESDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2020-04-10"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2020-04-13"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2020-05-01"), Day.WEDNESDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2020-05-21"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2020-06-01"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2020-06-11"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2020-10-03"), Day.THURSDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2020-11-01"), Day.FRIDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2020-12-25"), Day.WEDNESDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2020-12-26"), Day.THURSDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2021-01-01"), Day.SUNDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2021-04-07"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2021-04-10"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2021-05-01"), Day.MONDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2021-05-18"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2021-05-29"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2021-06-08"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2021-10-03"), Day.TUESDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2021-11-01"), Day.WEDNESDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2021-12-25"), Day.MONDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2021-12-26"), Day.TUESDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2022-01-01"), Day.SATURDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2022-04-15"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2022-04-18"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2022-05-01"), Day.SUNDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2022-05-26"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2022-06-06"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2022-06-16"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2022-10-03"), Day.MONDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2022-11-01"), Day.TUESDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2022-12-25"), Day.SUNDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2022-12-26"), Day.MONDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2023-01-01"), Day.SUNDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2023-04-07"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2023-04-10"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2023-05-01"), Day.MONDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2023-05-18"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2023-05-29"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2023-06-08"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2023-10-03"), Day.TUESDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2023-11-01"), Day.WEDNESDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2023-12-25"), Day.MONDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2023-12-26"), Day.TUESDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2024-01-01"), Day.MONDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2024-03-29"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2024-04-01"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2024-05-01"), Day.WEDNESDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2024-05-09"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2024-05-20"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2024-05-30"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2024-10-03"), Day.THURSDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2024-11-01"), Day.FRIDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2024-12-25"), Day.WEDNESDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2024-12-26"), Day.THURSDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2025-01-01"), Day.TUESDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2025-04-18"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2025-04-21"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2025-05-01"), Day.THURSDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2025-05-29"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2025-06-09"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2025-06-19"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2025-10-03"), Day.FRIDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2025-11-01"), Day.SATURDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2025-12-25"), Day.THURSDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2025-12-26"), Day.FRIDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2026-01-01"), Day.THURSDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2026-04-03"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2026-04-06"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2026-05-01"), Day.FRIDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2026-05-14"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2026-05-25"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2026-06-04"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2026-10-03"), Day.SATURDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2026-11-01"), Day.SUNDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2026-12-25"), Day.FRIDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2026-12-26"), Day.SATURDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2027-01-01"), Day.FRIDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2027-03-26"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2027-03-29"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2027-05-01"), Day.SATURDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2027-05-06"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2027-05-17"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2027-05-27"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2027-10-03"), Day.SUNDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2027-11-01"), Day.MONDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2027-12-25"), Day.SATURDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2027-12-26"), Day.SUNDAY));

            holidays.add(new Holiday("New Year´s Day", formatter.parse("2028-01-01"), Day.SATURDAY));
            holidays.add(new Holiday("Good Friday", formatter.parse("2028-04-14"), Day.FRIDAY));
            holidays.add(new Holiday("Easter Monday", formatter.parse("2028-04-17"), Day.MONDAY));
            holidays.add(new Holiday("Labour Day", formatter.parse("2028-05-01"), Day.MONDAY));
            holidays.add(new Holiday("Ascencion Day", formatter.parse("2028-06-05"), Day.THURSDAY));
            holidays.add(new Holiday("Whit Monday", formatter.parse("2028-06-15"), Day.MONDAY));
            holidays.add(new Holiday("Corpus Christi", formatter.parse("2028-06-15"), Day.THURSDAY));
            holidays.add(new Holiday("Day of German Unity", formatter.parse("2028-10-03"), Day.TUESDAY));
            holidays.add(new Holiday("All Saints'' Day", formatter.parse("2028-11-01"), Day.WEDNESDAY));
            holidays.add(new Holiday("Christmas", formatter.parse("2028-12-25"), Day.MONDAY));
            holidays.add(new Holiday("Second Day of Christmas", formatter.parse("2028-12-26"), Day.TUESDAY));
            
            holidays.forEach(h -> {
                holidayAccess.create(Holiday.toEntity(h));
            });
        }
        catch (ParseException ex) {
            Logger.getLogger(AppSystem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void truncateData() {
        holidayAccess.truncate();
        timesheetEntryAccess.truncate();
        timesheetAccess.truncate();
        roleAccess.truncate();
        contractAccess.truncate();
        personAccess.truncate();
    }

}
