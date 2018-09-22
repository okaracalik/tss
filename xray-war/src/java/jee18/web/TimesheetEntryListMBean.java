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
import javax.faces.context.FacesContext;
import jee18.dto.TimesheetEntry;
import jee18.logic.ITimesheetEntrySystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "timesheetEntryListMBean")
@RequestScoped
public class TimesheetEntryListMBean {

    @EJB(beanName = "TimesheetEntrySystem")
    private ITimesheetEntrySystem timesheetEntrySystem;

    private List<TimesheetEntry> timesheetEntryList;
    private final String emailAddress = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();

    public TimesheetEntryListMBean() {
    }

    public List<TimesheetEntry> getTimesheetEntryList() {
        if (timesheetEntryList == null) {
            timesheetEntryList = timesheetEntrySystem.listMyTimesheetEntries(emailAddress);
        }
        return timesheetEntryList;
    }

}
