/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee18.dao.PersonAccess;
import jee18.dto.Person;
import jee18.entities.PersonEntity;
import jee18.logic.TimesheetSystemLocal;

/**
 *
 * @author okaracalik
 */
@Stateless
public class TimeSheetSystem implements TimesheetSystemLocal {

    @EJB
    private PersonAccess pa;

    @Override
    public List<Person> getPersonList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Person createPerson(Person p) {
        PersonEntity pe = PersonEntity.newInstance();
        pe.setFirstName(p.getFirstName());
        pe.setLastName(p.getLastName());
        pe.setEmailAddress(p.getEmailAddress());
        pe.setDateOfBirth(p.getDateOfBirth());
        return createPersonTO(pa.addPerson(pe));
    }

    private Person createPersonTO(PersonEntity pe) {
        Person to = new Person();
        to.setFirstName(pe.getFirstName());
        to.setLastName(pe.getLastName());
        to.setEmailAddress(pe.getEmailAddress());
        to.setDateOfBirth(pe.getDateOfBirth());
        return to;
    }
}
