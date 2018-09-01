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
import jee18.dto.Timesheet;
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "timesheetFormMBean")
@ViewScoped
public class TimesheetFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB(beanName = "TimesheetSystem")
    private ITimesheetSystem timesheetSystem;

    private Timesheet timesheet;

    public TimesheetFormMBean() {
    }

    @PostConstruct
    public void init() {
        timesheet = new Timesheet();
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void createTimesheet() {
        try {
            timesheetSystem.create(timesheet);
            System.out.println(timesheet);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
