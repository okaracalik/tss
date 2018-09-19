/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dto.Timesheet;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.TimesheetStatus;
import jee18.logic.AbstractTimesheetSystem;
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
        return Timesheet.toEntity(t);
    }
    
    @Override
    protected Timesheet convertToObject(TimesheetEntity te) {
        return Timesheet.toDTO(te);
    }
    
    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public List<Timesheet> list() {
        return super.getList();
    }
    
    @RolesAllowed("EMPLOYEE")
    @Override
    public List<Timesheet> listMyTimesheets() {
        return super.getList();
    }
    
    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT"})
    @Override
    public Timesheet add(Timesheet t) {
        return super.create(t);
    }
    
    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public Timesheet get(String uuid) {
        System.out.print("Timesheet get: " + uuid);
        Timesheet t = super.getByUuid(uuid);
        System.out.print("Timesheet get: " + t);
        return t;
    }
    
    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer update(String uuid, Timesheet t) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() != TimesheetStatus.ARCHIVED) {
            return super.updateByUuid(uuid, t);
        }
        else {
            throw new EJBException("Timesheet is already archived.");
        }
    }
    
    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer delete(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() == TimesheetStatus.IN_PROGRESS) {
            return super.deleteByUuid(uuid);
        }
        else {
            throw new EJBException("Timesheet must be in progess.");
        }
    }
    
    @RolesAllowed("EMPLOYEE")
    @Override
    public Integer signAsEmployee(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() == TimesheetStatus.IN_PROGRESS) {
            timesheet.setSignedByEmployee(new Date());
            timesheet.setStatus(TimesheetStatus.SIGNED_BY_EMPLOYEE);
            return super.updateByUuid(uuid, timesheet);
        }
        else {
            throw new EJBException("Timesheet must be in progess.");
        }
    }
    
    @RolesAllowed("EMPLOYEE")
    @Override
    public Integer revokeEmployeeSignature(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() != TimesheetStatus.ARCHIVED) {
            return setStatusToInProgress(uuid);
        }
        else {
            throw new EJBException("Timesheet is already archived.");
        }
    }
    
    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer signAsSupervisor(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() == TimesheetStatus.SIGNED_BY_EMPLOYEE) {
            timesheet.setSignedBySupervisor(new Date());
            timesheet.setStatus(TimesheetStatus.SIGNED_BY_SUPERVISOR);
            return super.updateByUuid(uuid, timesheet);
        }
        else {
            throw new EJBException("Timesheet must be signed by employee.");
        }
    }
    
    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer requestChanges(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() == TimesheetStatus.SIGNED_BY_EMPLOYEE) {
            return setStatusToInProgress(uuid);
        }
        else {
            throw new EJBException("Timesheet must be signed by employee.");
        }
    }
    
    @RolesAllowed("SECRETARY")
    @Override
    public Integer setStatusToArchived(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        if (timesheet.getStatus() == TimesheetStatus.SIGNED_BY_SUPERVISOR) {
            timesheet.setStatus(TimesheetStatus.ARCHIVED);
            System.out.println("jee18.logic.impl.TimesheetSystem.setStatusToArchived()");
            return super.updateByUuid(uuid, timesheet);
        }
        else {
            throw new EJBException("Timesheet must be signed by supervisor.");
        }
    }
    
    @RolesAllowed("SECRETARY")
    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private Integer setStatusToInProgress(String uuid) {
        Timesheet timesheet = super.getByUuid(uuid);
        timesheet.setSignedByEmployee(null);
        timesheet.setSignedBySupervisor(null);
        timesheet.setStatus(TimesheetStatus.IN_PROGRESS);
        return super.updateByUuid(uuid, timesheet);
    }
    
}
