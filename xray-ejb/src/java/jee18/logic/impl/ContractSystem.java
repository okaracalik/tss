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

    // TODO: Update/Delete only PREPARED
    // TODO: Start contract
    // TODO: Terminate contract
    // TODO: Archive contract
    public ContractSystem() throws NamingException {
        super("ContractAccess");
    }

    @Override
    protected ContractEntity convertToEntity(Contract c) {
        ContractEntity ce = ContractEntity.newInstance();
        ce.setStatus(c.getStatus());
        ce.setName(c.getName());
        ce.setStartDate(DateTimeUtil.setToFirstDayOfMonth(DateTimeUtil.convertDateToLocalDate(c.getStartDate())));
        ce.setEndDate(DateTimeUtil.setToLastDayOfMonth(DateTimeUtil.convertDateToLocalDate(c.getEndDate())));
        ce.setFrequency(c.getFrequency());
        ce.setHoursPerWeek(c.getHoursPerWeek());
        ce.setWorkingDaysPerWeek(c.getWorkingDaysPerWeek());
        ce.setVacationDaysPerYear(c.getVacationDaysPerYear());
        ce.setVacationHours(calculateVacationHours(ce.getVacationDaysPerYear(), DateTimeUtil.calculateDurationOfContract(ce.getStartDate(), ce.getEndDate()), ce.getHoursPerWeek(), ce.getWorkingDaysPerWeek()));
        ce.setHoursDue(calculateHoursDue());
        return ce;
    }

    @Override
    protected Contract convertToObject(ContractEntity ce) {
        Contract to = new Contract();
        to.setUuid(ce.getUuid());
        to.setStatus(ce.getStatus());
        to.setName(ce.getName());
        to.setStartDate(DateTimeUtil.convertLocalDateToDate(ce.getStartDate()));
        to.setEndDate(DateTimeUtil.convertLocalDateToDate(ce.getEndDate()));
        to.setFrequency(ce.getFrequency());
        to.setHoursPerWeek(ce.getHoursPerWeek());
        //to.setTerminationDate(convertLocalDateToDate(ce.getTerminationDate()));
        to.setWorkingDaysPerWeek(ce.getWorkingDaysPerWeek());
        to.setVacationDaysPerYear(ce.getVacationDaysPerYear());
        to.setVacationHours(ce.getVacationHours());
        to.setHoursDue(ce.getVacationHours());
        return to;
    }

    // vacationHours = vacationDaysPerYear * durationOfContract / 12 * hoursPerWeek / workingDaysPerWeek  (The duration of the contract is counted in months.)
    private Double calculateVacationHours(Integer vacationDaysPerYear, Integer durationOfContract, Double hoursPerWeek, Integer workingDaysPerWeek) {
        return (vacationDaysPerYear.doubleValue() * (durationOfContract.doubleValue() / 12)) * (hoursPerWeek / workingDaysPerWeek.doubleValue());
    }

    // FIXME: calculateHoursDue
    private Double calculateHoursDue() {
        return 0.00;
    }

}
