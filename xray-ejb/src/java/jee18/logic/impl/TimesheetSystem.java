/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.naming.NamingException;
import jee18.dto.Timesheet;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.TimesheetStatus;
import jee18.logic.AbstractTimesheetSystem;
import jee18.utils.DateTimeUtil;
import jee18.logic.ITimesheetManagementSystem;
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "TimesheetSystem")
public class TimesheetSystem extends AbstractTimesheetSystem<Timesheet, TimesheetEntity> implements ITimesheetSystem {

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
        to.setUuid(te.getUuid());
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

    @Override
    public List<Timesheet> list() {
        return super.getList();
    }

    @Override
    public Timesheet add(Timesheet t) {
        return super.create(t);
    }

    @Override
    public Timesheet get(String uuid) {
        return super.getByUuid(uuid);
    }

    @Override
    public Integer update(String uuid, Timesheet t) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() != TimesheetStatus.ARCHIVED) {
            return super.updateByUuid(uuid, t);
        }
        else {
            return -1;
        }
    }

    @Override
    public Integer delete(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() == TimesheetStatus.IN_PROGRESS ) {
            return super.deleteByUuid(uuid);
        }
        else {
            return -1;
        }
    }

    @Override
    public Integer setStatusToInProgress(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        timesheet.setSignedByEmployee(null);
        timesheet.setSignedBySupervisor(null);
        timesheet.setStatus(TimesheetStatus.IN_PROGRESS);
        return super.updateByUuid(uuid, timesheet);
    }

    @Override
    public Integer setStatusToSignedByEmployee(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        timesheet.setSignedByEmployee(new Date());
        timesheet.setStatus(TimesheetStatus.SIGNED_BY_EMPLOYEE);
        return super.updateByUuid(uuid, timesheet);
    }

    @Override
    public Integer revokeEmployeeSignature(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        return setStatusToInProgress(uuid);
    }

    @Override
    public Integer setStatusToSignedBySupervisor(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        timesheet.setSignedBySupervisor(new Date());
        timesheet.setStatus(TimesheetStatus.SIGNED_BY_SUPERVISOR);
        return super.updateByUuid(uuid, timesheet);
    }

    @Override
    public Integer requestChanges(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        return setStatusToInProgress(uuid);
    }

    @Override
    public Integer setStatusToArchived(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        timesheet.setStatus(TimesheetStatus.ARCHIVED);
        return super.updateByUuid(uuid, timesheet);
    }

    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
