/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jee18.dto.Holiday;
import jee18.logic.IContractSystem;

/**
 *
 * @author esha
 */
@Named(value = "holidayMBean")
@RequestScoped
public class HolidayMBean {
     @EJB
    private IContractSystem contractSystem;

    private List<Holiday> holidayList;

    public HolidayMBean() {
    }

    @PostConstruct
    public void getHolidayList() {
//        if (holidayList == null) {
//            holidayList = contractSystem.calculatePublicHolidaysInPeriod("2");
//        }        
    }
    public void calculateHoursDue(String uuid)
    {
    contractSystem.calculateStatistics(uuid);
    }
    public List<Holiday> getHolidaysList() {
        return holidayList;
    }

    
}
