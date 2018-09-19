/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import jee18.dto.TimesheetEntry;

/**
 *
 * @author okaracalik
 */
public interface ITimesheetEntrySystem {

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    public List<TimesheetEntry> list();
    
    // FIXME: employee, may be supervisor, assistant
    // -EMPLOYEE ***
    public List<TimesheetEntry> listMyTimesheetEntries(String employeeUuid);

    // FIXME: employee, may be supervisor, assistant
    // -EMPLOYEE
    // RULE: if timesheet in progress
    public TimesheetEntry add(TimesheetEntry te, String timesheetUuid);

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    public TimesheetEntry get(String uuid);

    // FIXME: employee, may be supervisor, assistant
    // -EMPLOYEE ***
    // public TimesheetEntry getMyTimesheetEntry(String uuid);
    
    // FIXME: owner
    // -EMPLOYEE ***
    // RULE: if timesheet in progress
    public Integer update(String uuid, TimesheetEntry t);

    // FIXME: owner
    // -EMPLOYEE ***
    // RULE: if timesheet in progress
    public Integer delete(String uuid);

}
