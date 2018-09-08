/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import jee18.dto.TimesheetEntry;
import jee18.logic.ICRUD;

/**
 *
 * @author okaracalik
 */
@Named(value = "timesheetEntryListMBean")
@RequestScoped
public class TimesheetEntryListMBean {

    @EJB(beanName = "TimesheetEntrySystem")
    private ICRUD timesheetEntrySystem;

    private List<TimesheetEntry> timesheetEntryList;

    public TimesheetEntryListMBean() {
    }

    public List<TimesheetEntry> getTimesheetEntryList() {
        if (timesheetEntryList == null) {
            timesheetEntryList = timesheetEntrySystem.getList();
        }
        return timesheetEntryList;
    }

}
