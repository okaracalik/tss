/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import jee18.entities.TimesheetEntryEntity;
import jee18.entities.enums.ReportType;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
public class TimesheetEntry implements Serializable {

    private static final long serialVersionUID = 3419675164523830830L;

    private String uuid;
    private ReportType type;
    private String description;
    private Double hours;
    private Date startTime;
    private Date endTime;
    private Date entryDate;
    private Timesheet timesheet;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ReportType getType() {
        return type;
    }

    public void setType(ReportType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(Timesheet timesheet) {
        this.timesheet = timesheet;
    }

    @Override
    public String toString() {
        return "TimesheetEntry{" + "type=" + type + ", description=" + description + ", hours=" + hours + ", startTime=" + startTime + ", endTime=" + endTime + ", entryDate=" + entryDate + '}';
    }

    public static TimesheetEntryEntity toEntity(TimesheetEntry dto) {
        TimesheetEntryEntity e = TimesheetEntryEntity.newInstance();
        e.setType(dto.getType());
        e.setDescription(dto.getDescription());
        e.setStartTime(DateTimeUtil.convertDateToLocalTime(dto.getStartTime()));
        e.setEndTime(DateTimeUtil.convertDateToLocalTime(dto.getEndTime()));
        Duration d = Duration.between(e.getStartTime(), e.getEndTime());
        Double h = (double) d.toMinutes() / 60;
        e.setHours(h);
        e.setEntryDate(DateTimeUtil.convertDateToLocalDate(dto.getEntryDate()));
//        e.setTimesheet(Timesheet.toEntity(dto.getTimesheet()));
        return e;
    }

    public static TimesheetEntry toDTO(TimesheetEntryEntity e) {
        TimesheetEntry dto = new TimesheetEntry();
        dto.setUuid(e.getUuid());
        dto.setType(e.getType());
        dto.setDescription(e.getDescription());
        dto.setHours(e.getHours());
        dto.setStartTime(DateTimeUtil.convertLocalTimeToDate(e.getStartTime(), e.getEntryDate()));
        dto.setEndTime(DateTimeUtil.convertLocalTimeToDate(e.getEndTime(), e.getEntryDate()));
        dto.setEntryDate(DateTimeUtil.convertLocalDateToDate(e.getEntryDate()));
//        System.out.println(e);
        dto.setTimesheet(Timesheet.toDTO(e.getTimesheet()));
        return dto;
    }

}
