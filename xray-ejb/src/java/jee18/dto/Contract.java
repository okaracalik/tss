/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import jee18.entities.ContractEntity;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.TimesheetFrequency;
import jee18.utils.DateTimeUtil;

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
    private Double hoursDue = 0.0; // auto
    private Integer workingDaysPerWeek = 5; // default
    private Integer vacationDaysPerYear = 20; // default
    private Set<Timesheet> timesheets = new HashSet<>(); // default

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

    public Set<Timesheet> getTimesheets() {
        return timesheets;
    }

    public void setTimesheets(Set<Timesheet> timesheets) {
        this.timesheets = timesheets;
    }

    @Override
    public String toString() {
        return "Contract{" + "uuid=" + uuid + ", status=" + status + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", frequency=" + frequency + ", terminationDate=" + terminationDate + ", hoursPerWeek=" + hoursPerWeek + ", vacationHours=" + vacationHours + ", hoursDue=" + hoursDue + ", workingDaysPerWeek=" + workingDaysPerWeek + ", vacationDaysPerYear=" + vacationDaysPerYear + '}';
    }

    public static ContractEntity toEntity(Contract dto) {
        ContractEntity e = ContractEntity.newInstance();
        e.setStatus(dto.getStatus());
        e.setName(dto.getName());
        e.setStartDate(DateTimeUtil.setToFirstDayOfMonth(DateTimeUtil.convertDateToLocalDate(dto.getStartDate())));
        e.setEndDate(DateTimeUtil.setToLastDayOfMonth(DateTimeUtil.convertDateToLocalDate(dto.getEndDate())));
        e.setFrequency(dto.getFrequency());
        e.setHoursPerWeek(dto.getHoursPerWeek());
        e.setTerminationDate(DateTimeUtil.convertDateToLocalDate(dto.getTerminationDate()));
        e.setWorkingDaysPerWeek(dto.getWorkingDaysPerWeek());
        e.setVacationDaysPerYear(dto.getVacationDaysPerYear());
        e.setVacationHours(calculateVacationHours(e.getVacationDaysPerYear(), DateTimeUtil.calculateDurationOfContract(e.getStartDate(), e.getEndDate()), e.getHoursPerWeek(), e.getWorkingDaysPerWeek()));
        e.setHoursDue(dto.getHoursDue());
        dto.getTimesheets().stream().map(x -> Timesheet.toEntity(x)).collect(Collectors.toList()).forEach(t -> {
            e.addTimesheets(t);
        });
        return e;
    }

    public static Contract toDTO(ContractEntity e) {
        Contract dto = new Contract();
        dto.setUuid(e.getUuid());
        dto.setStatus(e.getStatus());
        dto.setName(e.getName());
        dto.setStartDate(DateTimeUtil.convertLocalDateToDate(e.getStartDate()));
        dto.setEndDate(DateTimeUtil.convertLocalDateToDate(e.getEndDate()));
        dto.setFrequency(e.getFrequency());
        dto.setHoursPerWeek(e.getHoursPerWeek());
        dto.setTerminationDate(DateTimeUtil.convertLocalDateToDate(e.getTerminationDate()));
        dto.setWorkingDaysPerWeek(e.getWorkingDaysPerWeek());
        dto.setVacationDaysPerYear(e.getVacationDaysPerYear());
        dto.setVacationHours(e.getVacationHours());
        dto.setHoursDue(e.getVacationHours());
//        dto.setTimesheets(e.getTimesheets().stream().map(x -> convertToTimesheetObject(x)).collect(Collectors.toSet()));
        return dto;
    }

    // vacationHours = vacationDaysPerYear * durationOfContract / 12 * hoursPerWeek / workingDaysPerWeek  (The duration of the contract is counted in months.)
    private static Double calculateVacationHours(Integer vacationDaysPerYear, Integer durationOfContract, Double hoursPerWeek, Integer workingDaysPerWeek) {
        return (vacationDaysPerYear.doubleValue() * (durationOfContract.doubleValue() / 12)) * (hoursPerWeek / workingDaysPerWeek.doubleValue());
    }

}
