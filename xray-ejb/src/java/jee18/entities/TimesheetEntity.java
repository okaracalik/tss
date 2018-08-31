/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jee18.entities.enums.TimesheetStatus;

/**
 *
 * @author okaracalik
 */
@Entity
@Table(name = "timesheets")
@NamedQueries({
    @NamedQuery(
            name = "TimesheetEntity.getTimesheetList",
            query = "SELECT p FROM TimesheetEntity p"
    )
})
public class TimesheetEntity extends AbstractEntity {

    private static final long serialVersionUID = 8164978510161170906L;

    @Enumerated(EnumType.STRING)
    private TimesheetStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double hoursDue;
    private LocalDate signedByEmployee;
    private LocalDate signedBySupervisor;

    public TimesheetEntity() {
        this(false);
    }

    TimesheetEntity(boolean isNew) {
        super(isNew);
    }

    public static TimesheetEntity newInstance() {
        return new TimesheetEntity(true);
    }

    public TimesheetStatus getStatus() {
        return status;
    }

    public void setStatus(TimesheetStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getHoursDue() {
        return hoursDue;
    }

    public void setHoursDue(Double hoursDue) {
        this.hoursDue = hoursDue;
    }

    public LocalDate getSignedByEmployee() {
        return signedByEmployee;
    }

    public void setSignedByEmployee(LocalDate signedByEmployee) {
        this.signedByEmployee = signedByEmployee;
    }

    public LocalDate getSignedBySupervisor() {
        return signedBySupervisor;
    }

    public void setSignedBySupervisor(LocalDate signedBySupervisor) {
        this.signedBySupervisor = signedBySupervisor;
    }

}
