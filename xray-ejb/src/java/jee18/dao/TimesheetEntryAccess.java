/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import jee18.entities.TimesheetEntity;
import jee18.entities.TimesheetEntryEntity;

/**
 *
 * @author okaracalik
 */
@LocalBean
@Stateless(name = "TimesheetEntryAccess")
public class TimesheetEntryAccess extends AbstractAccess implements IAccess<TimesheetEntryEntity> {

    @EJB
    private TimesheetAccess timesheetAccess;

    public TimesheetEntryAccess() {
        super(TimesheetEntryAccess.class);
    }

    public TimesheetEntryEntity createWithTimesheet(TimesheetEntryEntity te, String t) {
        TimesheetEntity a = timesheetAccess.getByUuid(t);
        em.persist(te);
        te.setTimesheet(a);
        a.addEntry(te);
        return te;
    }

    @Override
    public TimesheetEntryEntity create(TimesheetEntryEntity timesheetEntry) {
        em.persist(timesheetEntry);
        return timesheetEntry;
    }

    @Override
    public List<TimesheetEntryEntity> getList() {
        return em.createNamedQuery("TimesheetEntryEntity.getTimesheetEntryList", TimesheetEntryEntity.class).getResultList();
    }

    @Override
    public TimesheetEntryEntity getByUuid(String uuid) {
        try {
            return em.createNamedQuery("TimesheetEntryEntity.getTimesheetEntryEntityByUUID", TimesheetEntryEntity.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
     public List<TimesheetEntryEntity> getByTimesheetList(List<TimesheetEntity> timesheets) {
        try {
            return em.createNamedQuery("TimesheetEntryEntity.getTimesheetEntryEntityByTimesheetList", TimesheetEntryEntity.class)
                    .setParameter("timesheets", timesheets)
                    .getResultList();
    
        }
        catch (NoResultException e) {
            return null;
        }
    }
    

    @Override
    public Integer updateByUuid(String uuid, TimesheetEntryEntity timesheetEntry) {
        return em.createNamedQuery("TimesheetEntryEntity.updateTimesheetEntryEntityByUUID", TimesheetEntryEntity.class)
                .setParameter("uuid", uuid)
                .setParameter("type", timesheetEntry.getType())
                .setParameter("description", timesheetEntry.getDescription())
                .setParameter("hours", timesheetEntry.getHours())
                .setParameter("startTime", timesheetEntry.getStartTime())
                .setParameter("endTime", timesheetEntry.getEndTime())
                .setParameter("entryDate", timesheetEntry.getEntryDate())
                .executeUpdate();
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("TimesheetEntryEntity.deleteTimesheetEntryEntityByUUID", TimesheetEntryEntity.class)
                .setParameter("uuid", uuid).executeUpdate();
    }

}
