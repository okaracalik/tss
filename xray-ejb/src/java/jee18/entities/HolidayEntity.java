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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import jee18.entities.enums.Day;

/**
 *
 * @author esha
 */
@Table(name= "Holidays")
@Entity
@NamedQueries({
    @NamedQuery(
            name = "HolidayEntity.getHolidayList",
            query = "SELECT e FROM HolidayEntity e"
    ),
        @NamedQuery(
        name="HolidayEntity.getHolidayByPeriod",
        query = "SELECT e FROM HolidayEntity e WHERE e.holidayDate BETWEEN :startDate AND :endDate"
        )
})
public class HolidayEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String name;
     @Enumerated(EnumType.STRING)
    private Day dayOfWeek;
    private LocalDate holidayDate;

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

     
     public static HolidayEntity newInstance() {
        return new HolidayEntity();
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(LocalDate holidayDate) {
        this.holidayDate = holidayDate;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return "ok";
    }
    
}
