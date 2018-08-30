/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee18.dao.ContractAccess;
import jee18.dao.PersonAccess;
import jee18.dto.Contract;
import jee18.dto.Person;
import jee18.entities.ContractEntity;
import jee18.entities.PersonEntity;
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
        ce.setName(c.getName());
        ce.setStartDate(convertDateToLocalDate(c.getStartDate()));
        ce.setEndDate(convertDateToLocalDate(c.getEndDate()));
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
        to.setName(ce.getName());
        to.setStartDate(convertLocalDateToDate(ce.getStartDate()));
        to.setEndDate(convertLocalDateToDate(ce.getEndDate()));
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

    // UTILS
    private LocalDate convertDateToLocalDate(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Date convertLocalDateToDate(LocalDate d) {
        return java.sql.Date.valueOf(d);
    }

}
