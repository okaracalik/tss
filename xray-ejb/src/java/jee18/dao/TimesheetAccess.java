/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import jee18.entities.TimesheetEntity;

/**
 *
 * @author okaracalik
 */
@Stateless
public class TimesheetAccess extends AbstractAccess implements IAccess<TimesheetEntity> {

    public TimesheetAccess() {
        super(TimesheetEntity.class);
    }

    public TimesheetEntity addTimesheet(TimesheetEntity timesheet) {
        em.persist(timesheet);
        return timesheet;
    }

    public List<TimesheetEntity> getTimesheetList() {
        return em.createNamedQuery("TimesheetEntity.getTimesheetList", TimesheetEntity.class).getResultList();
    }

    @Override
    public TimesheetEntity create(TimesheetEntity timesheet) {
        em.persist(timesheet);
        return timesheet;
    }

    @Override
    public List<TimesheetEntity> getList() {
        return em.createNamedQuery("TimesheetEntity.getTimesheetList", TimesheetEntity.class).getResultList();
    }

    @Override
    public TimesheetEntity getByUuid(String uuid) {
        try {
            return em.createNamedQuery("TimesheetEntity.getTimesheetEntityByUUID", TimesheetEntity.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Integer updateByUuid(String uuid, TimesheetEntity timesheet) {
        return em.createNamedQuery("PersonEntity.updatePersonEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid)
                .setParameter("status", timesheet.getStatus())
                .setParameter("startDate", timesheet.getStartDate())
                .setParameter("endDate", timesheet.getEndDate())
                .setParameter("hoursDue", timesheet.getHoursDue())
                .setParameter("signedByEmployee", timesheet.getSignedByEmployee())
                .setParameter("signedBySupervisor", timesheet.getSignedBySupervisor())
                .executeUpdate();
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("TimesheetEntity.deleteTimesheetEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid).executeUpdate();
    }

}
