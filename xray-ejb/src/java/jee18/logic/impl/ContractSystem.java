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
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dto.Contract;
import jee18.dto.Holiday;
import jee18.dto.Timesheet;
import jee18.entities.ContractEntity;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.Day;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.IContractSystem;
import jee18.logic.IHolidaySystem;
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

    // vacationHours = vacationDaysPerYear * durationOfContract / 12 * hoursPerWeek / workingDaysPerWeek  (The duration of the contract is counted in months.)
    private Double calculateVacationHours(Integer vacationDaysPerYear, Integer durationOfContract, Double hoursPerWeek, Integer workingDaysPerWeek) {
        return (vacationDaysPerYear.doubleValue() * (durationOfContract.doubleValue() / 12)) * (hoursPerWeek / workingDaysPerWeek.doubleValue());
    }

    @Override
    public List<Holiday> calculatePublicHolidaysInPeriod(String uuid,Date startDate,Date endDate)      
    {
    Contract contract = super.getByUuid(uuid);
     
  
        if (contract.getStatus() == ContractStatus.PREPARED) {
        List<Holiday> holidayList = holidaySystem.calculatePublicHolidaysInPeriod("2");
        Holiday publicHoliday;
        
        //Excluding public holidays on Saturday and Sunday
        for (int i=0;i<holidayList.size();i++)
        {
          publicHoliday  = holidayList.get(i);
        if(publicHoliday.getDayOfWeek()==Day.SATURDAY || publicHoliday.getDayOfWeek()== Day.SUNDAY)
        {
        holidayList.remove(i);
        }
        if(!(publicHoliday.getHolidayDate().before(endDate) && publicHoliday.getHolidayDate().after(startDate)))
        {
        holidayList.remove(i);
        }
        }
        System.out.println("The number of holidays are :"+holidayList.size());
        
        return holidayList;
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }
    // FIXME: calculateHoursDue
    @Override
    public Double calculateHoursDue(String uuid) {
        Contract contract = super.getByUuid(uuid);
        double totalHoursDue=0;
        Timesheet timesheet;
        //get all timesheets for this contractID
         List<Timesheet> timesheets= timesheetSystem.getByContractId(contract.getId());
        for(int i=0;i<timesheets.size();i++)
        {
             timesheet = timesheets.get(i);
        //call gethoursdue for each time sheet
        Double hoursPerWeek = contract.getHoursPerWeek();
        Integer workingDaysPerWeek = contract.getWorkingDaysPerWeek();
        Date startDate = timesheet.getStartDate();
        Date endDate= timesheet.getEndDate();
        long diff = endDate.getTime() - startDate.getTime();
        long daysInPeriod = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+1;
        long nonWorkingDaysInPeriod=calculateNonWorkingDaysBetweenDates(startDate,endDate);
        int numberOfPublicHolidays = calculatePublicHolidaysInPeriod(uuid,startDate,endDate).size();
        long workingDaysInPeriod = daysInPeriod-nonWorkingDaysInPeriod;
        System.out.println("working Days In Period"+workingDaysInPeriod);
        double hoursDue = ((workingDaysInPeriod - numberOfPublicHolidays )* hoursPerWeek) /(workingDaysPerWeek);
        System.out.println("Hours Due"+hoursDue);
        totalHoursDue+=hoursDue;
        }
        return totalHoursDue; 
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
            Set<Timesheet> ts = dates.entrySet().stream().map(x -> {
                Timesheet t = new Timesheet();
                t.setStartDate(DateTimeUtil.convertLocalDateToDate(x.getKey()));
                t.setEndDate(DateTimeUtil.convertLocalDateToDate(x.getValue()));
                return t;
            }).collect(Collectors.toSet());
            contract.setTimesheets(ts);
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

    private long calculateNonWorkingDaysBetweenDates(Date startDate, Date endDate) {
      Calendar c1 = Calendar.getInstance();
        c1.setTime(startDate);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(endDate);

        int sundays = 0;
        int saturday = 0;

        while (! c1.after(c2)) {
            if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ){
                saturday++; 
            }
            if(c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                sundays++;
            }

            c1.add(Calendar.DATE, 1);
        }

        System.out.println("Saturday Count = "+saturday);
        System.out.println("Sunday Count = "+sundays);
        return saturday + sundays;
    }

}
