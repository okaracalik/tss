/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dto.TimesheetEntry;
import jee18.entities.TimesheetEntryEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.ICRUD;

/**
 *
 * @author okaracalik
 */
@Stateless(name="TimesheetEntrySystem")
public class TimesheetEntrySystem extends AbstractTimesheetSystem<TimesheetEntry, TimesheetEntryEntity> implements ICRUD<TimesheetEntry> {

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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
