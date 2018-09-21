/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import jee18.entities.enums.Day;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author esha
 */

public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Date holidayDate;
    private Day dayOfWeek;

    public Day getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getHolidayDate() {
        return holidayDate;
    }

    public void setHolidayDate(Date holidayDate) {
        this.holidayDate = holidayDate;
    }

   
    
        public static jee18.entities.HolidayEntity toEntity(Holiday dto) {
        jee18.entities.HolidayEntity e = jee18.entities.HolidayEntity.newInstance();
        e.setId(dto.getId());
        e.setName(dto.getName());
        e.setDayOfWeek(dto.getDayOfWeek());
        e.setHolidayDate(DateTimeUtil.convertDateToLocalDate(dto.getHolidayDate()));
        return e;
    }
    
    public static Holiday toDTO(jee18.entities.HolidayEntity e) {
        Holiday dto = new Holiday();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setDayOfWeek(e.getDayOfWeek());
        dto.setHolidayDate(DateTimeUtil.convertLocalDateToDate(e.getHolidayDate()));
        return dto;
    }
    
    
}
