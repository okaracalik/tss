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

    public List<TimesheetEntry> list();

    public TimesheetEntry add(TimesheetEntry te, String timesheetUuid);

    public TimesheetEntry get(String uuid);

    public Integer update(String uuid, TimesheetEntry t);

    public Integer delete(String uuid);

public List<TimesheetEntry> getByTimesheetList(List<Timesheet> timesheets) ;

}
