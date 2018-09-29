/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import jee18.dto.Timesheet;
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "timesheetListMBean")
@RequestScoped
public class TimesheetListMBean {

    @EJB
    private ITimesheetSystem timesheetSystem;

    private List<Timesheet> timesheetList;
    private final SimpleDateFormat df = new SimpleDateFormat("d MMM ''yy");


    private final String emailAddress = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();

    public TimesheetListMBean() {
    }

    public List<Timesheet> getTimesheetList() {
        if (timesheetList == null) {
            timesheetList = timesheetSystem.listMyTimesheets(emailAddress);
        }
        return timesheetList;
    }

    public String formatDate(Date date) throws ParseException {
        return df.format(date);
    }
    
    

}
