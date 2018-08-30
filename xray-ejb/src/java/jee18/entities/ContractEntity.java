/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author okaracalik
 */
@Entity
@Table(name = "contracts")
public class ContractEntity extends AbstractEntity {

    private static final long serialVersionUID = 8164978510161170907L;
    // TODO: contract status
    // TODO: timesheet frequency
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate terminationDate;
    private Double hoursPerWeek;
    private Double vacationHours;
    private Double hoursDue;

    public ContractEntity() {
        this(false);
    }

    ContractEntity(boolean isNew) {
        super(isNew);
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

    public Integer getVacationDaysPerWeek() {
        return vacationDaysPerWeek;
    }

    public void setVacationDaysPerWeek(Integer vacationDaysPerWeek) {
        this.vacationDaysPerWeek = vacationDaysPerWeek;
    }
    private Integer workingDaysPerWeek;
    private Integer vacationDaysPerWeek;
    
}
