/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dto.Person;
import jee18.entities.PersonEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.ITimesheetSystem;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "PersonSystem")
public class PersonSystem extends AbstractTimesheetSystem<Person, PersonEntity> implements ITimesheetSystem<Person> {

    public PersonSystem() throws NamingException {
        super("PersonAccess");
    }

    @Override
    protected PersonEntity convertToEntity(Person p) {
        PersonEntity pe = PersonEntity.newInstance();
        pe.setFirstName(p.getFirstName());
        pe.setLastName(p.getLastName());
        pe.setEmailAddress(p.getEmailAddress());
        pe.setDateOfBirth(DateTimeUtil.convertDateToLocalDate(p.getDateOfBirth()));
        return pe;
    }

    @Override
    protected Person convertToObject(PersonEntity pe) {
        Person to = new Person();
        to.setUuid(pe.getUuid());
        to.setFirstName(pe.getFirstName());
        to.setLastName(pe.getLastName());
        to.setEmailAddress(pe.getEmailAddress());
        to.setDateOfBirth(DateTimeUtil.convertLocalDateToDate(pe.getDateOfBirth()));
        return to;
    }

}
