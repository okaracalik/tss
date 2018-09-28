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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
            query = "SELECT e FROM TimesheetEntryEntity e"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntryEntity.getTimesheetEntryEntityByUUID",
            query = "SELECT e FROM TimesheetEntryEntity e WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntryEntity.updateTimesheetEntryEntityByUUID",
            query = "UPDATE TimesheetEntryEntity e SET e.type = :type, e.description = :description, e.hours = :hours, e.startTime = :startTime, e.endTime = :endTime, e.entryDate = :entryDate WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntryEntity.deleteTimesheetEntryEntityByUUID",
            query = "DELETE FROM TimesheetEntryEntity e WHERE e.uuid = :uuid"
    ),
    @NamedQuery(name="TimesheetEntryEntity.getTimesheetEntryEntityByTimesheetList" ,
            query=" SELECT e FROM TimesheetEntryEntity e WHERE e.timesheet IN :timesheets")


    )
    ,
    @NamedQuery(
            name = "TimesheetEntryEntity.truncate",
            query = "DELETE FROM TimesheetEntryEntity"
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
    @ManyToOne
    @JoinColumn(name = "timesheet_id")
    private TimesheetEntity timesheet;

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

    public TimesheetEntity getTimesheet() {
        return timesheet;
    }

    public void setTimesheet(TimesheetEntity timesheet) {
        this.timesheet = timesheet;
    }

    @Override
    public String toString() {
        return "TimesheetEntryEntity{" + "type=" + type + ", description=" + description + ", hours=" + hours + ", startTime=" + startTime + ", endTime=" + endTime + ", entryDate=" + entryDate + ", timesheet=" + timesheet + '}';
    }

}
