/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author okaracalik
 */
public class Contract implements Serializable {

    private static final long serialVersionUID = 3419675164523830832L;

    private String name;
    private Date startDate;
    private Date endDate;
    private Date terminationDate; // onUpdate
    private Double hoursPerWeek;
    private Double vacationHours; // auto
    private Double hoursDue; // auto
    private Integer workingDaysPerWeek = 5; // default
    private Integer vacationDaysPerYear = 20; // default

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
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

    @Override
    public String toString() {
        return "Contract{" + "name=" + name + ", startDate=" + startDate + ", endDate=" + endDate + ", terminationDate=" + terminationDate + ", hoursPerWeek=" + hoursPerWeek + ", vacationHours=" + vacationHours + ", hoursDue=" + hoursDue + ", workingDaysPerWeek=" + workingDaysPerWeek + ", vacationDaysPerYear=" + vacationDaysPerYear + '}';
    }

}
