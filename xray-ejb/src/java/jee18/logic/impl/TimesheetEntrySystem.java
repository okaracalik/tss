/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.TimesheetEntryAccess;
import jee18.dto.Timesheet;
import jee18.dto.TimesheetEntry;
import jee18.entities.TimesheetEntity;
import jee18.entities.TimesheetEntryEntity;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.TimesheetStatus;
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

    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public List<TimesheetEntry> listMyTimesheetEntries(String emailAddress) {
        return timesheetEntryAccess.getMyTimesheetEntryList(emailAddress).stream().map(x -> TimesheetEntry.toDTO(x)).collect(Collectors.toList());
    }

    @RolesAllowed("EMPLOYEE")
    @Override
    public TimesheetEntry add(TimesheetEntry te, String timesheetUuid, String emailAddress) {
        Timesheet t = timesheetSystem.getMyTimesheet(timesheetUuid, emailAddress);
        if (t.getStatus() == TimesheetStatus.IN_PROGRESS && t.getContract().getStatus() == ContractStatus.STARTED) {
            TimesheetEntryEntity tee = timesheetEntryAccess.createWithTimesheet(TimesheetEntry.toEntity(te), timesheetUuid, emailAddress);
            return TimesheetEntry.toDTO(tee);
        }
        else {
            throw new EJBException("Timesheet Entry can only be added when timesheet is in progress and contract was started.");
        }
    }

    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public TimesheetEntry getMyTimesheetEntry(String uuid, String emailAddress) {
        return TimesheetEntry.toDTO(timesheetEntryAccess.getMyTimesheetEntryByUuid(uuid, emailAddress));
    }

    @RolesAllowed("EMPLOYEE")
    @Override
    public Integer update(String uuid, TimesheetEntry te, String emailAddress) {
        if (te.getTimesheet().getStatus() == TimesheetStatus.IN_PROGRESS && te.getTimesheet().getContract().getStatus() == ContractStatus.STARTED) {
            return timesheetEntryAccess.updateMyTimesheetEntryByUuid(uuid, TimesheetEntry.toEntity(te), emailAddress);
        }
        else {
            throw new EJBException("Timesheet Entry can only be added when timesheet is in progress and contract was started.");
        }
    }

    @RolesAllowed("EMPLOYEE")
    @Override
    public Integer delete(String uuid, String emailAddress) {
        TimesheetEntry te = super.getByUuid(uuid);
        if (te.getTimesheet().getStatus() == TimesheetStatus.IN_PROGRESS && te.getTimesheet().getContract().getStatus() == ContractStatus.STARTED) {
            return timesheetEntryAccess.deleteMyTimesheetEntryByUuid(uuid, emailAddress);
        }
        else {
            throw new EJBException("Timesheet Entry can only be added when timesheet is in progress and contract was started.");
        }
    }

    @Override
    public List<TimesheetEntry> getByTimesheetList(List<Timesheet> timesheets) {
     List<TimesheetEntryEntity>  timesheetentriesList=timesheetEntryAccess.getByTimesheetList(timesheetSystem.convertObjListToEntityList(timesheets)) ;
     return super.convertEntityListToObjectList(timesheetentriesList);
       
    }

}
