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
    // -EMPLOYEE ***
    public List<TimesheetEntry> list();

    // -EMPLOYEE
    // RULE: if timesheet in progress
    public TimesheetEntry add(TimesheetEntry te, String timesheetUuid);

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    // -EMPLOYEE ***
    public TimesheetEntry get(String uuid);

    // -EMPLOYEE ***
    // RULE: if timesheet in progress
    public Integer update(String uuid, TimesheetEntry t);

    // -EMPLOYEE ***
    // RULE: if timesheet in progress
    public Integer delete(String uuid);

}
