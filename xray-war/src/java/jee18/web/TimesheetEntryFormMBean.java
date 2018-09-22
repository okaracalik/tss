/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jee18.dto.TimesheetEntry;
import jee18.logic.ITimesheetEntrySystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "timesheetEntryFormMBean")
@ViewScoped
public class TimesheetEntryFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB(beanName = "TimesheetEntrySystem")
    private ITimesheetEntrySystem timesheetEntrySystem;
    private String uuid;
    private String timesheetUuid;
    private final String emailAddress = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    private TimesheetEntry timesheetEntry;

    public TimesheetEntryFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            timesheetEntry = new TimesheetEntry();
        }
        else {
            timesheetEntry = (TimesheetEntry) timesheetEntrySystem.getMyTimesheetEntry(uuid, emailAddress);
        }
    }

    public TimesheetEntry getTimesheetEntry() {
        return timesheetEntry;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String getTimesheetUuid() {
        return timesheetUuid;
    }

    public void setTimesheetUuid(String timesheetUuid) {
        this.timesheetUuid = timesheetUuid;
    }

    public String save() {
        try {
            if (uuid == null) {
                timesheetEntrySystem.add(timesheetEntry, timesheetUuid, emailAddress);
            }
            else {
                timesheetEntrySystem.update(uuid, timesheetEntry, emailAddress);
            }
            return "/timesheet-entry/timesheet-entry-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String delete() {
        try {
            timesheetEntrySystem.delete(uuid, emailAddress);
            return "/timesheet-entry/timesheet-entry-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

}
