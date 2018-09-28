/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import jee18.entities.ContractEntity;
import jee18.entities.PersonEntity;
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
    private PersonAccess personAccess;

    @EJB
    ContractAccess ca;

    public TimesheetAccess() {
        super(TimesheetEntity.class);
    }

    public List<TimesheetEntity> getMyTimesheetList(String emailAddress) {
        List<ContractEntity> cs = ca.getMyContractList(emailAddress);
        return cs.stream().flatMap(x -> x.getTimesheets().stream()).collect(Collectors.toList());
    }

    public TimesheetEntity getMyTimesheetByUuid(String uuid, String emailAddress) {
        System.out.println("jee18.dao.TimesheetAccess.getMyTimesheetByUuid()");
        System.out.println(uuid);
        TimesheetEntity t = em.createNamedQuery("TimesheetEntity.getTimesheetEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid)
                .getSingleResult();
        System.out.println(t);
        // if timesheet's contract exists
        List<ContractEntity> c = Arrays.asList(t.getContract());
        PersonEntity p = personAccess.getByEmailAddress(emailAddress);
        List<ContractEntity> ce = ContractAccess.findMyContracts(c, p);
        if (ce != null && ce.size() == 1) {
            return t;
        }
        else {
            throw new EJBException("User is not authorized on timesheet.");
        }
    }

    public Integer updateMyTimesheetByUuid(String uuid, TimesheetEntity timesheet, String emailAddress) {
        TimesheetEntity t = getMyTimesheetByUuid(uuid, emailAddress);
        if (t != null) {
            Integer rows = updateByUuid(uuid, timesheet);
            if (timesheet.getStatus() == TimesheetStatus.ARCHIVED) {
                Set<TimesheetEntity> ts = t.getContract().getTimesheets().stream().filter(x -> x.getStatus() != TimesheetStatus.ARCHIVED).collect(Collectors.toSet());
                String firstUuid = ts.iterator().next().getUuid();
                if (ts.size() == 1 && firstUuid.equals(uuid)) {
                    ca.archive(t.getContract().getUuid(), emailAddress);
                }
            }
            return rows;
        }
        else {
            throw new EJBException("User is not authorized on timesheet.");
        }
    }

    public Integer deleteMyTimesheetByUuid(String uuid, String emailAddress) {
        TimesheetEntity t = getMyTimesheetByUuid(uuid, emailAddress);
        if (t != null) {
            return deleteByUuid(uuid);
        }
        else {
            throw new EJBException("User is not authorized on timesheet.");
        }
    }

    public List<TimesheetEntity> getTimesheetListByEndDateAndStatus(LocalDate endDate, TimesheetStatus timesheetStatus) {
        return em.createNamedQuery("TimesheetEntity.getTimesheetEntityByEndDateAndStatus", TimesheetEntity.class).setParameter("endDate", endDate).setParameter("status", timesheetStatus).getResultList();
    }

    public List<TimesheetEntity> getTimesheetListByStatus(TimesheetStatus timesheetStatus) {
        return em.createNamedQuery("TimesheetEntity.getTimesheetEntityByStatus", TimesheetEntity.class).setParameter("timesheetStatus", timesheetStatus).getResultList();
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
        Integer rows = em.createNamedQuery("TimesheetEntity.updateTimesheetEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid)
                .setParameter("status", timesheet.getStatus())
                .setParameter("startDate", timesheet.getStartDate())
                .setParameter("endDate", timesheet.getEndDate())
                .setParameter("hoursDue", timesheet.getHoursDue())
                .setParameter("signedByEmployee", timesheet.getSignedByEmployee())
                .setParameter("signedBySupervisor", timesheet.getSignedBySupervisor())
                .executeUpdate();

        return rows;
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("TimesheetEntity.deleteTimesheetEntityByUUID", TimesheetEntity.class)
                .setParameter("uuid", uuid).executeUpdate();
    }
    
 public List<TimesheetEntity>  getByContractId(long id)
 {
 return em.createNamedQuery("TimesheetEntity.getTimesheetListByContractId", TimesheetEntity.class)
         .setParameter("contract_id", id)
         .getResultList();
 }
}
