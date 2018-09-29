/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.ContractAccess;
import jee18.dao.TimesheetAccess;
import jee18.dto.Contract;
import jee18.dto.Holiday;
import jee18.dto.Timesheet;
import jee18.entities.ContractEntity;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.Day;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.IContractSystem;
import jee18.logic.IHolidaySystem;
import jee18.logic.ITimesheetEntrySystem;
import jee18.logic.ITimesheetSystem;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "ContractSystem")
public class ContractSystem extends AbstractTimesheetSystem<Contract, ContractEntity> implements IContractSystem {

    @EJB
    private TimesheetAccess timesheetAccess;

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private ITimesheetSystem timesheetSystem;

    @EJB
    private ITimesheetEntrySystem timesheetEntrySystem;

    @EJB
    private IHolidaySystem holidaySystem;

    public ContractSystem() throws NamingException {
        super("ContractAccess");
    }

    @Override
    protected ContractEntity convertToEntity(Contract c) {
        return Contract.toEntity(c);
    }

    @Override
    protected Contract convertToObject(ContractEntity ce) {
        return Contract.toDTO(ce);
    }

    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public List<Contract> listMyContracts(String emailAddress) {
        return contractAccess.getMyContractList(emailAddress).stream().map(x -> convertToObject(x)).collect(Collectors.toList());
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Contract add(Contract c, List<String> secretaryUuids, String employeeUuid, String supervisorUuid, List<String> assistantUuids) {
        return Contract.toDTO(contractAccess.createWithPersons(Contract.toEntity(c), secretaryUuids, employeeUuid, supervisorUuid, assistantUuids));
    }

    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public Contract getMyContract(String uuid, String emailAddress) {
        return Contract.toDTO(contractAccess.getMyContractByUuid(uuid, emailAddress));
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer update(String uuid, Contract c, String emailAddress) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            return super.updateByUuid(uuid, c);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer delete(String uuid, String emailAddress) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            return super.deleteByUuid(uuid);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer setStatusToStarted(String uuid, String emailAddress) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            HashMap<LocalDate, LocalDate> dates = DateTimeUtil.findTimesheetDates(DateTimeUtil.convertDateToLocalDate(contract.getStartDate()), DateTimeUtil.convertDateToLocalDate(contract.getEndDate()), contract.getFrequency());
            Set<Timesheet> ts = dates.entrySet().stream().map(x -> {
                Timesheet t = new Timesheet();
                t.setStartDate(DateTimeUtil.convertLocalDateToDate(x.getKey()));
                t.setEndDate(DateTimeUtil.convertLocalDateToDate(x.getValue()));
                return t;
            }).collect(Collectors.toSet());

            return contractAccess.start(uuid, ts.stream().map(x -> Timesheet.toEntity(x)).collect(Collectors.toSet()), emailAddress);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }

    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer setStatusToTerminated(String uuid, String emailAddress) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.STARTED) {
            return contractAccess.terminate(uuid, emailAddress);
        }
        else {
            throw new EJBException("Contract must be in started status.");
        }
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer setStatusToArchived(String uuid, String emailAddress) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.STARTED || contract.getStatus() == ContractStatus.TERMINATED) {
            return contractAccess.archive(uuid, emailAddress);
        }
        else {
            throw new EJBException("Contract must be either started or terminated.");
        }
    }

    @RolesAllowed("SECRETARY")
    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // FIXME: update contract, timesheet: hoursDue
    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public HashMap<String, Double> calculateStatistics(String uuid, String emailAddress) {
        ContractEntity ce = contractAccess.getMyContractByUuid(uuid, emailAddress);
        List<TimesheetEntity> tes = timesheetAccess.getTimesheetListByContract(uuid, emailAddress);

        HashMap<String, Double> statistics = new HashMap<>();

        double totalHoursDue = tes.stream().map(te -> {
            Date startDate = DateTimeUtil.convertLocalDateToDate(te.getStartDate());
            Date endDate = DateTimeUtil.convertLocalDateToDate(te.getEndDate());
            long duration = endDate.getTime() - startDate.getTime();
            long daysInPeriod = TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS) + 1;
            long nonWorkingDaysInPeriod = calculateNonWorkingDaysBetweenDates(startDate, endDate);
            int numberOfPublicHolidays = calculatePublicHolidaysInPeriod(Contract.toDTO(ce), startDate, endDate).size();
            long workingDaysInPeriod = daysInPeriod - nonWorkingDaysInPeriod;
            return ((workingDaysInPeriod - numberOfPublicHolidays) * ce.getHoursPerWeek()) / (ce.getWorkingDaysPerWeek());
        }).reduce(0.00, (y, z) -> y + z);

        double hoursWorked = tes.stream().map(te -> te.getEntries().stream().map(tee -> tee.getHours()).reduce(0.00, (x, y) -> x + y)).reduce(0.00, (a, b) -> a + b);

        double balance = hoursWorked - totalHoursDue;
        statistics.put("TotalHoursDue", totalHoursDue);
        statistics.put("Balance", balance);

        return statistics;
    }

    private List<Holiday> calculatePublicHolidaysInPeriod(Contract contract, Date startDate, Date endDate) {

        if (contract.getStatus() != ContractStatus.PREPARED) {
            List<Holiday> holidayList = holidaySystem.calculatePublicHolidaysInPeriod(contract.getStartDate(), contract.getEndDate());
            endDate = new Date(endDate.getTime() + (24 * 60 * 60 * 1000));
            startDate = new Date(startDate.getTime() - (24 * 60 * 60 * 1000));
            //    int numberOfHolidays=holidayList.size();
            //Excluding public holidays on Saturday and Sunday
            for (Iterator<Holiday> it = holidayList.iterator(); it.hasNext();) {
                Holiday publicHoliday = it.next();
                if (publicHoliday.getDayOfWeek() == Day.SATURDAY || publicHoliday.getDayOfWeek() == Day.SUNDAY) {
                    it.remove();
                }
                if (!(publicHoliday.getHolidayDate().before(endDate) && publicHoliday.getHolidayDate().after(startDate))) {
                    it.remove();
                }
            }
            System.out.println("The number of holidays are :" + holidayList.size());

            return holidayList;
        }
        else {
            throw new EJBException("Contract must be in started status at least.");
        }
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
