/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.time.LocalDate;
import javax.persistence.Entity;

/**
 *
 * @author okaracalik
 */
@Entity
public class TimesheetEntryEntity extends AbstractEntity {
    
    private static final long serialVersionUID = 8164978510161170905L;
    // TODO: report type
    private String description;
    private Double hours;
    private LocalDate startTime;
    private LocalDate endTime;
    private LocalDate entryDate;

    public TimesheetEntryEntity() {
        this(false);
    }

    TimesheetEntryEntity(boolean isNew) {
        super(isNew);
    }

    public static TimesheetEntryEntity newInstance() {
        return new TimesheetEntryEntity(true);
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

    public LocalDate getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }
    
}
