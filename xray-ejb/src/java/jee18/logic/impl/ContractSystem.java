/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dto.Contract;
import jee18.entities.ContractEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.ITimesheetSystem;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "ContractSystem")
public class ContractSystem extends AbstractTimesheetSystem<Contract, ContractEntity> implements ITimesheetSystem<Contract> {

    public ContractSystem() throws NamingException {
        super("ContractAccess");
    }

    @Override
    protected ContractEntity convertToEntity(Contract c) {
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

    @Override
    protected Contract convertToObject(ContractEntity ce) {
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
}
