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
import jee18.entities.ContractEntity;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.TimesheetStatus;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
public class Timesheet implements Serializable {

    private static final long serialVersionUID = 3419675164523830831L;

    private String uuid;
    private TimesheetStatus status = TimesheetStatus.IN_PROGRESS;
    private Date startDate;
    private Date endDate;
    private Double hoursDue;
    private Date signedByEmployee;
    private Date signedBySupervisor;
    private Contract contract;
    private Set<TimesheetEntry> entries = new HashSet<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TimesheetStatus getStatus() {
        return status;
    }

    public void setStatus(TimesheetStatus status) {
        this.status = status;
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

    public Double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(Double hoursDue) {
        this.hoursDue = hoursDue;
    }

    public Date getSignedByEmployee() {
        return signedByEmployee;
    }

    public void setSignedByEmployee(Date signedByEmployee) {
        this.signedByEmployee = signedByEmployee;
    }

    public Date getSignedBySupervisor() {
        return signedBySupervisor;
    }

    public void setSignedBySupervisor(Date signedBySupervisor) {
        this.signedBySupervisor = signedBySupervisor;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Set<TimesheetEntry> getEntries() {
        return entries;
    }

    public void setEntries(Set<TimesheetEntry> entries) {
        this.entries = entries;
    }

    @Override
    public String toString() {
        return "Timesheet{" + "uuid=" + uuid + ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", hoursDue=" + hoursDue + ", signedByEmployee=" + signedByEmployee + ", signedBySupervisor=" + signedBySupervisor + ", contract=" + contract + ", entries=" + entries + '}';
    }


    public static TimesheetEntity toEntity(Timesheet dto) {
        TimesheetEntity e = TimesheetEntity.newInstance();
        e.setStatus(dto.getStatus());
        e.setStartDate(DateTimeUtil.convertDateToLocalDate(dto.getStartDate()));
        e.setEndDate(DateTimeUtil.convertDateToLocalDate(dto.getEndDate()));
        e.setHoursDue(calculateTimesheetHoursDue());
        e.setSignedByEmployee(DateTimeUtil.convertDateToLocalDate(dto.getSignedByEmployee()));
        e.setSignedBySupervisor(DateTimeUtil.convertDateToLocalDate(dto.getSignedBySupervisor()));
        // dto.getEntries().stream().map(x -> TimesheetEntry.toEntity(x)).collect(Collectors.toList()).forEach(t -> {
        //     e.addEntry(t);
        // });
        return e;
    }

    // TODO: does not convert entries
    public static Timesheet toDTO(TimesheetEntity e) {
        Timesheet dto = new Timesheet();
        dto.setUuid(e.getUuid());
        dto.setStatus(e.getStatus());
        dto.setStartDate(DateTimeUtil.convertLocalDateToDate(e.getStartDate()));
        dto.setEndDate(DateTimeUtil.convertLocalDateToDate(e.getEndDate()));
        dto.setHoursDue(e.getHoursDue());
        dto.setSignedByEmployee(DateTimeUtil.convertLocalDateToDate(e.getSignedByEmployee()));
        dto.setSignedBySupervisor(DateTimeUtil.convertLocalDateToDate(e.getSignedBySupervisor()));
        dto.setContract(Contract.toDTO(e.getContract()));
        System.out.print("Timesheet toDTO: " + dto);
        System.out.print("Timesheet toDTO: " + dto.getContract());
        return dto;
    }
    
    private static Contract toContractDTO(ContractEntity e) {
        Contract dto = new Contract();
        
        return dto;
    }

    // FIXME: calculateTimesheetHoursDue
    private static Double calculateTimesheetHoursDue() {
        return 0.00;
    }

}
