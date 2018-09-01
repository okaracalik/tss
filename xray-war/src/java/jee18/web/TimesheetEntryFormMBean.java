/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jee18.dto.TimesheetEntry;
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "timesheetEntryFormMBean")
@ViewScoped
public class TimesheetEntryFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB(beanName = "TimesheetEntrySystem")
    private ITimesheetSystem timesheetEntrySystem;

    private TimesheetEntry timesheetEntry;

    public TimesheetEntryFormMBean() {
    }

    @PostConstruct
    public void init() {
        timesheetEntry = new TimesheetEntry();
    }

    public TimesheetEntry getTimesheetEntry() {
        return timesheetEntry;
    }

    public void createTimesheetEntry() {
        try {
            timesheetEntrySystem.create(timesheetEntry);
            System.out.println(timesheetEntry);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
