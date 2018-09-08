/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.util.Date;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.TimesheetFrequency;

/**
 *
 * @author okaracalik
 */
public class Contract implements Serializable {

    private static final long serialVersionUID = 3419675164523830832L;

    private String uuid;
    private ContractStatus status = ContractStatus.PREPARED;
    private String name;
    private Date startDate;
    private Date endDate;
    private TimesheetFrequency frequency = TimesheetFrequency.WEEKLY;
    private Date terminationDate; // onUpdate
    private Double hoursPerWeek;
    private Double vacationHours; // auto
    private Double hoursDue; // auto
    private Integer workingDaysPerWeek = 5; // default
    private Integer vacationDaysPerYear = 20; // default

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public Double getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(Double hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public Double getVacationHours() {
        return vacationHours;
    }

    public void setVacationHours(Double vacationHours) {
        this.vacationHours = vacationHours;
    }

    public Double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(Double hoursDue) {
        this.hoursDue = hoursDue;
    }

    public Integer getWorkingDaysPerWeek() {
        return workingDaysPerWeek;
    }

    public void setWorkingDaysPerWeek(Integer workingDaysPerWeek) {
        this.workingDaysPerWeek = workingDaysPerWeek;
    }

    public Integer getVacationDaysPerYear() {
        return vacationDaysPerYear;
    }

    public void setVacationDaysPerYear(Integer vacationDaysPerYear) {
        this.vacationDaysPerYear = vacationDaysPerYear;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
    }

    public TimesheetFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(TimesheetFrequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Contract{" + "uuid=" + uuid + ", status=" + status + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", frequency=" + frequency + ", terminationDate=" + terminationDate + ", hoursPerWeek=" + hoursPerWeek + ", vacationHours=" + vacationHours + ", hoursDue=" + hoursDue + ", workingDaysPerWeek=" + workingDaysPerWeek + ", vacationDaysPerYear=" + vacationDaysPerYear + '}';
    }

}
