/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
            query = "SELECT e FROM TimesheetEntity e"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.getTimesheetEntityByUUID",
            query = "SELECT e FROM TimesheetEntity e WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.updateTimesheetEntityByUUID",
            query = "UPDATE TimesheetEntity e SET e.status = :status, e.startDate = :startDate, e.endDate = :endDate, e.hoursDue = :hoursDue, e.signedByEmployee = :signedByEmployee, e.signedBySupervisor = :signedBySupervisor WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.deleteTimesheetEntityByUUID",
            query = "DELETE FROM TimesheetEntity e WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.deleteTimesheetEntityInProgressByContractId",
            query = "DELETE FROM TimesheetEntity e WHERE e.contract = :contract AND e.status = :status"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.getTimesheetEntityByContract",
            query = "SELECT e FROM TimesheetEntity e WHERE e.contract = :contract"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.getTimesheetEntityByContractAndNotStatus",
            query = "SELECT e FROM TimesheetEntity e WHERE e.contract = :contract AND e.status != :status"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.truncate",
            query = "DELETE FROM TimesheetEntity"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.getTimesheetEntityByEndDateAndStatus",
            query = "SELECT e FROM TimesheetEntity e WHERE e.endDate = :endDate AND e.status = :status"
    )
    ,
    @NamedQuery(
            name = "TimesheetEntity.getTimesheetEntityByStatus",
            query = "SELECT e FROM TimesheetEntity e WHERE e.status = :status"
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
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;
    @OneToMany(mappedBy = "timesheet", cascade = CascadeType.PERSIST)
    private Set<TimesheetEntryEntity> entries = new HashSet<>();

    public TimesheetEntity() {
        this(false);
    }

    TimesheetEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            entries = new HashSet<>();
        }
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

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

    public Set<TimesheetEntryEntity> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "TimesheetEntity{" + "status=" + status + ", startDate=" + startDate + ", endDate=" + endDate + ", hoursDue=" + hoursDue + ", signedByEmployee=" + signedByEmployee + ", signedBySupervisor=" + signedBySupervisor + ", contract=" + contract + '}';
    }
    
    public void addEntry(TimesheetEntryEntity e) {
        System.out.print(this.getClass().toString() + " addEntry: " + e);
        e.setTimesheet(this);
        entries.add(e);
    }

}
