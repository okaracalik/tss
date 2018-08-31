/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jee18.entities.enums.ReportType;

/**
 *
 * @author okaracalik
 */
@Entity
@Table(name = "timesheet_entries")
@NamedQueries({
    @NamedQuery(
            name = "TimesheetEntryEntity.getTimesheetEntryList",
            query = "SELECT p FROM TimesheetEntryEntity p"
    )
})
public class TimesheetEntryEntity extends AbstractEntity {

    private static final long serialVersionUID = 8164978510161170905L;

    @Enumerated(EnumType.STRING)
    private ReportType type;
    private String description;
    private Double hours;
    private LocalTime startTime;
    private LocalTime endTime;
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

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

}
