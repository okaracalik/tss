/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import jee18.entities.TimesheetEntity;
import jee18.entities.TimesheetEntryEntity;
import jee18.entities.enums.ReportType;

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
        TimesheetEntity timesheet = timesheetAccess.getByUuid(t);
        if (te.getType() == ReportType.VACATION) {
            Double vacationHours = timesheet.getContract().getVacationHours();
            Double usedVacationHours = timesheet.getEntries().stream().filter(x -> x.getType() == ReportType.VACATION).mapToDouble(TimesheetEntryEntity::getHours).sum() + te.getHours();
            System.out.println(usedVacationHours + " <= " + vacationHours);
            if (usedVacationHours < 0) {
                throw new EJBException("Invalid time inputs.");
            }
            else if (usedVacationHours <= vacationHours) {
                em.persist(te);
                te.setTimesheet(timesheet);
                timesheet.addEntry(te);
                return te;
            }
            else {
                throw new EJBException("Vacation hours exceeded.");
            }
        }
        else {
            em.persist(te);
            te.setTimesheet(timesheet);
            timesheet.addEntry(te);
            return te;
        }
    }

    public Integer truncate() {
        return em.createNamedQuery("TimesheetEntryEntity.truncate", TimesheetEntryEntity.class).executeUpdate();
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

    @Override
    public Integer updateByUuid(String uuid, TimesheetEntryEntity timesheetEntry) {
        if (timesheetEntry.getType() == ReportType.VACATION) {
            TimesheetEntity timesheet = timesheetAccess.getByUuid(timesheetEntry.getTimesheet().getUuid());
            Double vacationHours = timesheet.getContract().getVacationHours();
            Double usedVacationHours = timesheet.getEntries().stream().filter(x -> x.getType() == ReportType.VACATION).mapToDouble(TimesheetEntryEntity::getHours).sum() + timesheetEntry.getHours();
            System.out.println(usedVacationHours + " <= " + vacationHours);
            if (usedVacationHours < 0) {
                throw new EJBException("Invalid time inputs.");
            }
            else if (usedVacationHours <= vacationHours) {
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
            else {
                throw new EJBException("Vacation hours exceeded.");
            }
        }
        else {
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

    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("TimesheetEntryEntity.deleteTimesheetEntryEntityByUUID", TimesheetEntryEntity.class)
                .setParameter("uuid", uuid).executeUpdate();
    }

}
