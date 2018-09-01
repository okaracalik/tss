/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import jee18.entities.TimesheetEntryEntity;

/**
 *
 * @author okaracalik
 */
@Stateless(name="TimesheetEntryAccess")
public class TimesheetEntryAccess extends AbstractAccess implements IAccess<TimesheetEntryEntity> {

    public TimesheetEntryAccess() {
        super(TimesheetEntryAccess.class);
    }

    public TimesheetEntryEntity addTimesheetEntry(TimesheetEntryEntity timesheetEntry) {
        em.persist(timesheetEntry);
        return timesheetEntry;
    }

    public List<TimesheetEntryEntity> getTimesheetEntryList() {
        return em.createNamedQuery("TimesheetEntryEntity.getTimesheetEntryList", TimesheetEntryEntity.class).getResultList();
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

}
