/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dto.TimesheetEntry;
import jee18.entities.TimesheetEntryEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.utils.DateTimeUtil;
import jee18.logic.ITimesheetManagementSystem;

/**
 *
 * @author okaracalik
 */
@Stateless(name="TimesheetEntrySystem")
public class TimesheetEntrySystem extends AbstractTimesheetSystem<TimesheetEntry, TimesheetEntryEntity> implements ITimesheetManagementSystem<TimesheetEntry> {

    public TimesheetEntrySystem() throws NamingException {
        super("TimesheetEntryAccess");
    }

    @Override
    protected TimesheetEntryEntity convertToEntity(TimesheetEntry te) {
        TimesheetEntryEntity tee = TimesheetEntryEntity.newInstance();
        tee.setType(te.getType());
        tee.setDescription(te.getDescription());
        tee.setHours(te.getHours());
        tee.setStartTime(DateTimeUtil.convertDateToLocalTime(te.getStartTime()));
        tee.setEndTime(DateTimeUtil.convertDateToLocalTime(te.getEndTime()));
        tee.setEntryDate(DateTimeUtil.convertDateToLocalDate(te.getEntryDate()));
        return tee;
    }

    @Override
    protected TimesheetEntry convertToObject(TimesheetEntryEntity tee) {
        TimesheetEntry te = new TimesheetEntry();
        te.setUuid(tee.getUuid());
        te.setType(tee.getType());
        te.setDescription(tee.getDescription());
        te.setHours(tee.getHours());
        te.setStartTime(DateTimeUtil.convertLocalTimeToDate(tee.getStartTime(), tee.getEntryDate()));
        te.setEndTime(DateTimeUtil.convertLocalTimeToDate(tee.getEndTime(), tee.getEntryDate()));
        te.setEntryDate(DateTimeUtil.convertLocalDateToDate(tee.getEntryDate()));
        return te;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
