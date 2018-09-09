/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.IAccess;
import jee18.dto.Contract;
import jee18.dto.Timesheet;
import jee18.entities.ContractEntity;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.TimesheetFrequency;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.IContractSystem;
import jee18.logic.ITimesheetSystem;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "ContractSystem")
public class ContractSystem extends AbstractTimesheetSystem<Contract, ContractEntity> implements IContractSystem {

    @EJB
    private ITimesheetSystem timesheetSystem;

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
        ce.setTerminationDate(DateTimeUtil.convertDateToLocalDate(c.getTerminationDate()));
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
        to.setTerminationDate(DateTimeUtil.convertLocalDateToDate(ce.getTerminationDate()));
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

    @Override
    public List<Contract> list() {
        return super.getList();
    }

    @Override
    public Contract add(Contract c) {
        Contract contract = super.create(c);

        return contract;
    }

    @Override
    public Contract get(String uuid) {
        return super.getByUuid(uuid);
    }

    @Override
    public Integer update(String uuid, Contract c) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            return super.updateByUuid(uuid, c);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }

    @Override
    public Integer delete(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            return super.deleteByUuid(uuid);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }

    @Override
    public Integer setStatusToStarted(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            contract.setStatus(ContractStatus.STARTED);
            HashMap<LocalDate, LocalDate> dates = DateTimeUtil.findTimesheetDates(DateTimeUtil.convertDateToLocalDate(contract.getStartDate()), DateTimeUtil.convertDateToLocalDate(contract.getEndDate()), contract.getFrequency());
            List<Timesheet> ts = dates.entrySet().stream().map(x -> {
                Timesheet t = new Timesheet();
                t.setStartDate(DateTimeUtil.convertLocalDateToDate(x.getKey()));
                t.setEndDate(DateTimeUtil.convertLocalDateToDate(x.getValue()));
                return timesheetSystem.add(t);
            }).collect(Collectors.toList());
            return super.updateByUuid(uuid, contract);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }

    @Override
    public Integer setStatusToTerminated(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.STARTED) {
            contract.setTerminationDate(new Date());
            contract.setStatus(ContractStatus.TERMINATED);
            return super.updateByUuid(uuid, contract);
        }
        else {
            throw new EJBException("Contract must be in started status.");
        }
    }

    @Override
    public Integer setStatusToArchived(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.STARTED || contract.getStatus() == ContractStatus.TERMINATED) {
            contract.setStatus(ContractStatus.ARCHIVED);
            return super.updateByUuid(uuid, contract);
        }
        else {
            throw new EJBException("Contract must be either started or terminated.");
        }
    }

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
