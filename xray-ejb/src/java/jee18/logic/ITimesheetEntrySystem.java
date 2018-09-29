/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import jee18.dto.Timesheet;
import jee18.dto.TimesheetEntry;
import jee18.entities.TimesheetEntity;

/**
 *
 * @author okaracalik
 */
public interface ITimesheetEntrySystem {

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    // -EMPLOYEE ***
    public List<TimesheetEntry> listMyTimesheetEntries(String emailAddress);

    // -EMPLOYEE
    // RULE: if timesheet in progress
    public TimesheetEntry add(TimesheetEntry te, String timesheetUuid, String emailAddress);

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    // -EMPLOYEE ***
    public TimesheetEntry getMyTimesheetEntry(String uuid, String emailAddress);

    // -EMPLOYEE ***
    // RULE: if timesheet in progress
    public Integer update(String uuid, TimesheetEntry t, String emailAddress);

    // -EMPLOYEE ***
    // RULE: if timesheet in progress
    public Integer delete(String uuid, String emailAddress);

public List<TimesheetEntry> getByTimesheetList(List<Timesheet> timesheets) ;

}
