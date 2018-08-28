/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import jee18.entities.TimesheetEntryEntity;

/**
 *
 * @author okaracalik
 */
@Stateless
@LocalBean
public class TimesheetEntryAccess extends AbstractAccess {

    public TimesheetEntryAccess() {
        super(TimesheetEntryAccess.class);
    }
    
    public TimesheetEntryEntity addTimesheetEntry(TimesheetEntryEntity timesheetEntry) {
        em.persist(timesheetEntry);
        return timesheetEntry;
    }
        
}
