/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee18.dao.ContractAccess;
import jee18.dao.TimesheetAccess;
import jee18.dao.TimesheetEntryAccess;
import jee18.dto.Contract;
import jee18.dto.Timesheet;
import jee18.dto.TimesheetEntry;
import jee18.entities.ContractEntity;
import jee18.entities.TimesheetEntity;
import jee18.entities.TimesheetEntryEntity;
import jee18.logic.TimesheetSystemLocal;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
@Stateless
public class TimeSheetSystem implements TimesheetSystemLocal {

    @EJB
    private ContractAccess ca;

    @EJB
    private TimesheetAccess ta;

    @EJB
    private TimesheetEntryAccess tea;

    // CONTRACT
    @Override
    public List<Contract> getContractList() {
        return ca.getContractList().stream().map(x -> convertToContractObject(x)).collect(Collectors.toList());
    }

    @Override
    public Contract createContract(Contract c) {
        return convertToContractObject(ca.addContract(convertToContractEntity(c)));
    }

    private ContractEntity convertToContractEntity(Contract c) {
        ContractEntity ce = ContractEntity.newInstance();
        ce.setStatus(c.getStatus());
        ce.setName(c.getName());
        ce.setStartDate(DateTimeUtil.convertDateToLocalDate(c.getStartDate()));
        ce.setEndDate(DateTimeUtil.convertDateToLocalDate(c.getEndDate()));
        ce.setFrequency(c.getFrequency());
        ce.setHoursPerWeek(c.getHoursPerWeek());
        // FIXME: contract termination date: error when null
        // ce.setTerminationDate(DateTimeUtil.convertDateToLocalDate(c.getTerminationDate()));
        ce.setWorkingDaysPerWeek(c.getWorkingDaysPerWeek());
        ce.setVacationDaysPerYear(c.getVacationDaysPerYear());
        ce.setVacationHours(calculateVacationHours());
        ce.setHoursDue(calculateHoursDue());
        return ce;
    }

    private Contract convertToContractObject(ContractEntity ce) {
        Contract to = new Contract();
        to.setStatus(ce.getStatus());
        to.setName(ce.getName());
        to.setStartDate(DateTimeUtil.convertLocalDateToDate(ce.getStartDate()));
        to.setEndDate(DateTimeUtil.convertLocalDateToDate(ce.getEndDate()));
        to.setFrequency(ce.getFrequency());
        to.setHoursPerWeek(ce.getHoursPerWeek());
        //to.setTerminationDate(convertLocalDateToDate(ce.getTerminationDate()));
        to.setWorkingDaysPerWeek(to.getWorkingDaysPerWeek());
        to.setVacationDaysPerYear(to.getVacationDaysPerYear());
        to.setVacationHours(to.getVacationHours());
        to.setHoursDue(to.getVacationHours());
        return to;
    }

    // FIXME: calculateVacationHours
    private Double calculateVacationHours() {
        return 0.00;
    }

    // FIXME: calculateHoursDue
    private Double calculateHoursDue() {
        return 0.00;
    }

    // TIMESHEET
    @Override
    public List<Timesheet> getTimesheetList() {
        return ta.getTimesheetList().stream().map(x -> convertToTimesheetObject(x)).collect(Collectors.toList());
    }

    @Override
    public Timesheet createTimesheet(Timesheet t) {
        return convertToTimesheetObject(ta.addTimesheet(convertToTimesheetEntity(t)));
    }

    private TimesheetEntity convertToTimesheetEntity(Timesheet t) {
        TimesheetEntity te = TimesheetEntity.newInstance();
        te.setStatus(t.getStatus());
        te.setStartDate(DateTimeUtil.convertDateToLocalDate(t.getStartDate()));
        te.setEndDate(DateTimeUtil.convertDateToLocalDate(t.getEndDate()));
        te.setHoursDue(calculateTimesheetHoursDue());
        return te;
    }

    private Timesheet convertToTimesheetObject(TimesheetEntity te) {
        Timesheet to = new Timesheet();
        to.setStatus(te.getStatus());
        to.setStartDate(DateTimeUtil.convertLocalDateToDate(te.getStartDate()));
        to.setEndDate(DateTimeUtil.convertLocalDateToDate(te.getEndDate()));
        to.setHoursDue(te.getHoursDue());
        // FIXME: null dates
        // to.setSignedByEmployee(te.getSignedByEmployee());
        // to.setSignedBySupervisor(te.getSignedBySupervisor());
        return to;
    }

    // FIXME: calculateTimesheetHoursDue
    private Double calculateTimesheetHoursDue() {
        return 0.00;
    }

    // TIMESHEET ENTRY
    @Override
    public List<TimesheetEntry> getTimesheetEntryList() {
        return tea.getTimesheetEntryList().stream().map(x -> convertToTimesheetEntryObject(x)).collect(Collectors.toList());
    }

    @Override
    public TimesheetEntry createTimesheetEntry(TimesheetEntry te) {
        return convertToTimesheetEntryObject(tea.addTimesheetEntry(convertToTimesheetEntryEntity(te)));
    }

    private TimesheetEntryEntity convertToTimesheetEntryEntity(TimesheetEntry te) {
        TimesheetEntryEntity tee = TimesheetEntryEntity.newInstance();
        tee.setType(te.getType());
        tee.setDescription(te.getDescription());
        tee.setHours(te.getHours());
        tee.setStartTime(DateTimeUtil.convertDateToLocalTime(te.getStartTime()));
        tee.setEndTime(DateTimeUtil.convertDateToLocalTime(te.getEndTime()));
        tee.setEntryDate(DateTimeUtil.convertDateToLocalDate(te.getEntryDate()));
        return tee;
    }

    private TimesheetEntry convertToTimesheetEntryObject(TimesheetEntryEntity tee) {
        TimesheetEntry te = new TimesheetEntry();
        te.setType(tee.getType());
        te.setDescription(tee.getDescription());
        te.setHours(tee.getHours());
        te.setStartTime(DateTimeUtil.convertLocalTimeToDate(tee.getStartTime(), tee.getEntryDate()));
        te.setEndTime(DateTimeUtil.convertLocalTimeToDate(tee.getEndTime(), tee.getEntryDate()));
        te.setEntryDate(DateTimeUtil.convertLocalDateToDate(tee.getEntryDate()));
        return te;
    }

}
