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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.TimesheetFrequency;

/**
 *
 * @author okaracalik
 */
@Entity
@Table(name = "contracts")
@NamedQueries({
    @NamedQuery(
            name = "ContractEntity.getContractList",
            query = "SELECT c FROM ContractEntity c"
    )
    ,
    @NamedQuery(
            name = "ContractEntity.getContractEntityByUUID",
            query = "SELECT c FROM ContractEntity c WHERE c.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "ContractEntity.updateContractEntityByUUID",
            query = "UPDATE ContractEntity e SET e.status = :status, e.name = :name, e.startDate = :startDate, e.endDate = :endDate, e.frequency = :frequency, e.terminationDate = :terminationDate, e.hoursPerWeek = :hoursPerWeek, e.vacationHours = :vacationHours, e.hoursDue = :hoursDue, e.workingDaysPerWeek = :workingDaysPerWeek, e.vacationDaysPerYear = :vacationDaysPerYear WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "ContractEntity.deleteContractEntityByUUID",
            query = "DELETE FROM ContractEntity e WHERE e.uuid = :uuid"
    )
})
public class ContractEntity extends AbstractEntity {

    private static final long serialVersionUID = 8164978510161170907L;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private TimesheetFrequency frequency;
    private LocalDate terminationDate;
    private Double hoursPerWeek;
    private Double vacationHours;
    private Double hoursDue;
    private Integer workingDaysPerWeek;
    private Integer vacationDaysPerYear;
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<TimesheetEntity> timesheets;
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<Secretary> secretaries;
    @OneToOne(mappedBy = "contract", cascade = CascadeType.ALL)
    private Employee employee;
    @OneToOne(mappedBy = "contract", cascade = CascadeType.ALL)
    private Supervisor supervisor;
    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<Assistant> assistants;

    public ContractEntity() {
        this(false);
    }

    ContractEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            timesheets = new HashSet<>();
            secretaries = new HashSet<>();
            assistants = new HashSet<>();
        }
    }

    public static ContractEntity newInstance() {
        return new ContractEntity(true);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(LocalDate terminationDate) {
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

    public Set<TimesheetEntity> getTimesheets() {
        return timesheets;
    }

    public Set<Secretary> getSecretaries() {
        return secretaries;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public Set<Assistant> getAssistants() {
        return assistants;
    }

    @Override
    public String toString() {
        return "ContractEntity{" + "status=" + status + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", frequency=" + frequency + ", terminationDate=" + terminationDate + ", hoursPerWeek=" + hoursPerWeek + ", vacationHours=" + vacationHours + ", hoursDue=" + hoursDue + ", workingDaysPerWeek=" + workingDaysPerWeek + ", vacationDaysPerYear=" + vacationDaysPerYear + ", timesheets=" + timesheets + '}';
    }

    public void addTimesheets(TimesheetEntity te) {
        System.out.print(this.getClass().toString() + " addTimesheets: " + te);
        te.setContract(this);
        timesheets.add(te);
    }

}
