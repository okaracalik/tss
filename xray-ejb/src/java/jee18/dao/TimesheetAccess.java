/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.TimesheetStatus;

/**
 *
 * @author okaracalik
 */
@Stateless
@LocalBean
public class TimesheetAccess extends AbstractAccess implements IAccess<TimesheetEntity> {

    @EJB
    ContractAccess ca;

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
    
    public Integer truncate() {
        return em.createNamedQuery("TimesheetEntity.truncate", TimesheetEntity.class).executeUpdate();
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
        TimesheetEntity te = em.createNamedQuery("TimesheetEntity.getTimesheetEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid)
                .getSingleResult();

        Integer rows = em.createNamedQuery("TimesheetEntity.updateTimesheetEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid)
                .setParameter("status", timesheet.getStatus())
                .setParameter("startDate", timesheet.getStartDate())
                .setParameter("endDate", timesheet.getEndDate())
                .setParameter("hoursDue", timesheet.getHoursDue())
                .setParameter("signedByEmployee", timesheet.getSignedByEmployee())
                .setParameter("signedBySupervisor", timesheet.getSignedBySupervisor())
                .executeUpdate();

        if (timesheet.getStatus() == TimesheetStatus.ARCHIVED) {
            Set<TimesheetEntity> ts = te.getContract().getTimesheets().stream().filter(t -> t.getStatus() != TimesheetStatus.ARCHIVED).collect(Collectors.toSet());
            String firstUuid = ts.iterator().next().getUuid();
            if (ts.size() == 1 && firstUuid.equals(uuid)) {
                ca.archive(te.getContract().getUuid());
            }
        }
        return rows;
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("TimesheetEntity.deleteTimesheetEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid).executeUpdate();
    }

}
