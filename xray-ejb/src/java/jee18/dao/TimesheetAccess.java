/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import jee18.entities.TimesheetEntity;

/**
 *
 * @author okaracalik
 */
@Stateless
@LocalBean
public class TimesheetAccess extends AbstractAccess {

    public TimesheetAccess() {
        super(TimesheetEntity.class);
    }

    public TimesheetEntity addTimesheet(TimesheetEntity timesheet) {
        em.persist(timesheet);
        return timesheet;
    }

}
