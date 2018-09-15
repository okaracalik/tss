/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.TimesheetEntryAccess;
import jee18.dto.Timesheet;
import jee18.dto.TimesheetEntry;
import jee18.entities.TimesheetEntryEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.ITimesheetEntrySystem;
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "TimesheetEntrySystem")
public class TimesheetEntrySystem extends AbstractTimesheetSystem<TimesheetEntry, TimesheetEntryEntity> implements ITimesheetEntrySystem {

    @EJB
    private TimesheetEntryAccess timesheetEntryAccess;

    @EJB
    private ITimesheetSystem timesheetSystem;

    public TimesheetEntrySystem() throws NamingException {
        super("TimesheetEntryAccess");
    }

    @Override
    protected TimesheetEntryEntity convertToEntity(TimesheetEntry te) {
        return TimesheetEntry.toEntity(te);
    }

    @Override
    protected TimesheetEntry convertToObject(TimesheetEntryEntity tee) {
        return TimesheetEntry.toDTO(tee);
    }

    @Override
    public List<TimesheetEntry> list() {
        return super.getList();
    }

    @Override
    public TimesheetEntry add(TimesheetEntry te, String timesheetUuid) {
        Timesheet t = timesheetSystem.get(timesheetUuid);
//        Map<String, AbstractEntity> relations = new HashMap();
//        relations.put("TIMESHEET", Timesheet.toEntity(t));
//        return TimesheetEntry.toDTO((TimesheetEntryEntity) timesheetEntryAccess.create(TimesheetEntry.toEntity(te), relations));
        TimesheetEntryEntity tee = timesheetEntryAccess.createWithTimesheet(TimesheetEntry.toEntity(te), timesheetUuid);
        return TimesheetEntry.toDTO(tee);
    }

    @Override
    public TimesheetEntry get(String uuid) {
        return super.getByUuid(uuid);
    }

    @Override
    public Integer update(String uuid, TimesheetEntry t) {
        return super.updateByUuid(uuid, t);
    }

    @Override
    public Integer delete(String uuid) {
        return super.deleteByUuid(uuid);
    }

}
