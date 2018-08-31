/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee18.dao.ContractAccess;
import jee18.dao.PersonAccess;
import jee18.dao.TimesheetAccess;
import jee18.dao.TimesheetEntryAccess;
import jee18.dto.Contract;
import jee18.dto.Person;
import jee18.dto.Timesheet;
import jee18.dto.TimesheetEntry;
import jee18.entities.ContractEntity;
import jee18.entities.PersonEntity;
import jee18.entities.TimesheetEntity;
import jee18.entities.TimesheetEntryEntity;
import jee18.logic.TimesheetSystemLocal;

/**
 *
 * @author okaracalik
 */
@Stateless
public class TimeSheetSystem implements TimesheetSystemLocal {

    @EJB
    private PersonAccess pa;

    @EJB
    private ContractAccess ca;

    @EJB
    private TimesheetAccess ta;

    @EJB
    private TimesheetEntryAccess tea;

    // PERSON
    @Override
    public List<Person> getPersonList() {
        return pa.getPersonList().stream().map(x -> convertToPersonObject(x)).collect(Collectors.toList());
    }

    @Override
    public Person createPerson(Person p) {
        return convertToPersonObject(pa.addPerson(convertToPersonEntity(p)));
    }

    private PersonEntity convertToPersonEntity(Person p) {
        PersonEntity pe = PersonEntity.newInstance();
        pe.setFirstName(p.getFirstName());
        pe.setLastName(p.getLastName());
        pe.setEmailAddress(p.getEmailAddress());
        pe.setDateOfBirth(convertDateToLocalDate(p.getDateOfBirth()));
        return pe;
    }

    private Person convertToPersonObject(PersonEntity pe) {
        Person to = new Person();
        to.setFirstName(pe.getFirstName());
        to.setLastName(pe.getLastName());
        to.setEmailAddress(pe.getEmailAddress());
        to.setDateOfBirth(convertLocalDateToDate(pe.getDateOfBirth()));
        return to;
    }

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
        ce.setStartDate(convertDateToLocalDate(c.getStartDate()));
        ce.setEndDate(convertDateToLocalDate(c.getEndDate()));
        ce.setFrequency(c.getFrequency());
        ce.setHoursPerWeek(c.getHoursPerWeek());
        // FIXME: contract termination date: error when null
        // ce.setTerminationDate(convertDateToLocalDate(c.getTerminationDate()));
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
        to.setStartDate(convertLocalDateToDate(ce.getStartDate()));
        to.setEndDate(convertLocalDateToDate(ce.getEndDate()));
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
        te.setStartDate(convertDateToLocalDate(t.getStartDate()));
        te.setEndDate(convertDateToLocalDate(t.getEndDate()));
        te.setHoursDue(calculateTimesheetHoursDue());
        return te;
    }
    

    private Timesheet convertToTimesheetObject(TimesheetEntity te) {
        Timesheet to = new Timesheet();
        to.setStatus(te.getStatus());
        to.setStartDate(convertLocalDateToDate(te.getStartDate()));
        to.setEndDate(convertLocalDateToDate(te.getEndDate()));
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
        tee.setStartTime(convertDateToLocalTime(te.getStartTime()));
        tee.setEndTime(convertDateToLocalTime(te.getEndTime()));
        tee.setEntryDate(convertDateToLocalDate(te.getEntryDate()));
        return tee;
    }

    private TimesheetEntry convertToTimesheetEntryObject(TimesheetEntryEntity tee) {
        TimesheetEntry te = new TimesheetEntry();
        te.setType(tee.getType());
        te.setDescription(tee.getDescription());
        te.setHours(tee.getHours());
        te.setStartTime(convertLocalTimeToDate(tee.getStartTime(), tee.getEntryDate()));
        te.setEndTime(convertLocalTimeToDate(tee.getEndTime(), tee.getEntryDate()));
        te.setEntryDate(convertLocalDateToDate(tee.getEntryDate()));
        return te;
    }

    // UTILS
    private LocalDate convertDateToLocalDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date convertLocalDateToDate(LocalDate d) {
        return java.sql.Date.valueOf(d);
    }
    
    private LocalTime convertDateToLocalTime(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
    }
    
    private Date convertLocalTimeToDate(LocalTime d, LocalDate l) {
        return Date.from(d.atDate(l).atZone(ZoneId.systemDefault()).toInstant());
    }

}
