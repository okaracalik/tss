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
import jee18.entities.enums.Day;

/**
 *
 * @author esha
 */
@Table(name = "Holidays")
@Entity
@NamedQueries({
    @NamedQuery(
            name = "HolidayEntity.getHolidayList",
            query = "SELECT e FROM HolidayEntity e"
    )
    ,
    @NamedQuery(
            name = "HolidayEntity.getHolidayEntityByUUID",
            query = "SELECT e FROM HolidayEntity e WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "HolidayEntity.updateHolidayEntityByUUID",
            query = "UPDATE HolidayEntity e SET e.name = :name, e.dayOfWeek = :dayOfWeek, e.holidayDate = :holidayDate WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "HolidayEntity.deleteHolidayEntityByUUID",
            query = "DELETE FROM HolidayEntity e WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "HolidayEntity.truncate",
            query = "DELETE FROM HolidayEntity"
    )
    ,
    @NamedQuery(
            name = "HolidayEntity.getHolidayByPeriod",
            query = "SELECT e FROM HolidayEntity e WHERE e.holidayDate >= :startDate and e.holidayDate <= :endDate"
    )
})
public class HolidayEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    private String name;
    @Enumerated(EnumType.STRING)
    private Day dayOfWeek;
    private LocalDate holidayDate;

    public HolidayEntity() {
        this(false);
    }

    private HolidayEntity(boolean isNew) {
        super(isNew);
    }

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public static HolidayEntity newInstance() {
        return new HolidayEntity(true);
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

    @Override
    public String toString() {
        return "ok";
    }

}
