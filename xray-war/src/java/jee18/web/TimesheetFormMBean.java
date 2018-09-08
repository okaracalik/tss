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

    @EJB
    private ITimesheetSystem timesheetSystem;

    private Timesheet timesheet;
    private String uuid;

    public TimesheetFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            timesheet = new Timesheet();
        }
        timesheet = (Timesheet) timesheetSystem.get(uuid);
    }

    public Timesheet getTimesheet() {
        return timesheet;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String save() {
        try {
            if (uuid == null) {
                timesheetSystem.add(timesheet);
            }
            else {
                timesheetSystem.update(uuid, timesheet);
            }
            return "/person/person-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String delete() {
        try {
            timesheetSystem.delete(uuid);
            return "/person/person-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

}
