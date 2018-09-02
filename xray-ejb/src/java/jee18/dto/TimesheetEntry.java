/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.util.Date;
import jee18.entities.enums.ReportType;

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

    @Override
    public String toString() {
        return "TimesheetEntry{" + "type=" + type + ", description=" + description + ", hours=" + hours + ", startTime=" + startTime + ", endTime=" + endTime + ", entryDate=" + entryDate + '}';
    }

}
