/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.util.Date;
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

    @Override
    public String toString() {
        return "Timesheet{" + "uuid=" + uuid + ", status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", hoursDue=" + hoursDue + ", signedByEmployee=" + signedByEmployee + ", signedBySupervisor=" + signedBySupervisor + '}';
    }

    public static TimesheetEntity toEntity(Timesheet dto) {
        TimesheetEntity te = TimesheetEntity.newInstance();
        te.setStatus(dto.getStatus());
        te.setStartDate(DateTimeUtil.convertDateToLocalDate(dto.getStartDate()));
        te.setEndDate(DateTimeUtil.convertDateToLocalDate(dto.getEndDate()));
        te.setHoursDue(calculateTimesheetHoursDue());
        te.setSignedByEmployee(DateTimeUtil.convertDateToLocalDate(dto.getSignedByEmployee()));
        te.setSignedBySupervisor(DateTimeUtil.convertDateToLocalDate(dto.getSignedBySupervisor()));
        return te;
    }
    
    public static Timesheet toDTO(TimesheetEntity te) {
        Timesheet to = new Timesheet();
        to.setUuid(te.getUuid());
        to.setStatus(te.getStatus());
        to.setStartDate(DateTimeUtil.convertLocalDateToDate(te.getStartDate()));
        to.setEndDate(DateTimeUtil.convertLocalDateToDate(te.getEndDate()));
        to.setHoursDue(te.getHoursDue());
        to.setSignedByEmployee(DateTimeUtil.convertLocalDateToDate(te.getSignedByEmployee()));
        to.setSignedBySupervisor(DateTimeUtil.convertLocalDateToDate(te.getSignedBySupervisor()));
        return to;
    }
    
    // FIXME: calculateTimesheetHoursDue
    private static Double calculateTimesheetHoursDue() {
        return 0.00;
    }

}
