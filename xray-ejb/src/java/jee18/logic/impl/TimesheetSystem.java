/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.NamingException;
import jee18.dto.Timesheet;
import jee18.entities.TimesheetEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.ITimesheetSystem;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "TimesheetSystem")
public class TimesheetSystem extends AbstractTimesheetSystem<Timesheet, TimesheetEntity> implements ITimesheetSystem<Timesheet> {

    public TimesheetSystem() throws NamingException {
        super("TimesheetAccess");
    }

    @Override
    protected TimesheetEntity convertToEntity(Timesheet t) {
        TimesheetEntity te = TimesheetEntity.newInstance();
        te.setStatus(t.getStatus());
        te.setStartDate(DateTimeUtil.convertDateToLocalDate(t.getStartDate()));
        te.setEndDate(DateTimeUtil.convertDateToLocalDate(t.getEndDate()));
        te.setHoursDue(calculateTimesheetHoursDue());
        return te;
    }

    @Override
    protected Timesheet convertToObject(TimesheetEntity te) {
        Timesheet to = new Timesheet();
        to.setStatus(te.getStatus());
        to.setStartDate(DateTimeUtil.convertLocalDateToDate(te.getStartDate()));
        to.setEndDate(DateTimeUtil.convertLocalDateToDate(te.getEndDate()));
        to.setHoursDue(te.getHoursDue());
        // FIXME: null dates
        // to.setSignedByEmployee(te.getSignedByEmployee());
        // to.setSignedBySupervisor(te.getSignedBySupervisor());
        return to;
    }

    // FIXME: calculateTimesheetHoursDue
    private Double calculateTimesheetHoursDue() {
        return 0.00;
    }

}
