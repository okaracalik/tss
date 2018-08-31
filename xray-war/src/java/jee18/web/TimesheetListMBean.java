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
import jee18.dto.Timesheet;
import jee18.logic.TimesheetSystemLocal;

/**
 *
 * @author okaracalik
 */
@Named(value = "timesheetListMBean")
@RequestScoped
public class TimesheetListMBean {

    @EJB
    private TimesheetSystemLocal timeSheetSystem;

    private List<Timesheet> timesheetList;

    public TimesheetListMBean() {
    }

    public List<Timesheet> getTimesheetList() {
        if (timesheetList == null) {
            timesheetList = timeSheetSystem.getTimesheetList();
        }
        return timesheetList;
    }
    
}
