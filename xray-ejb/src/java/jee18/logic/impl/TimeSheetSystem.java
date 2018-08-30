/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
        return pa.getPersonList().stream().map(x -> convertToObject(x)).collect(Collectors.toList());
    }

    @Override
    public Person createPerson(Person p) {
        return convertToObject(pa.addPerson(convertToEntity(p)));
    }

    private PersonEntity convertToEntity(Person p) {
        PersonEntity pe = PersonEntity.newInstance();
        pe.setFirstName(p.getFirstName());
        pe.setLastName(p.getLastName());
        pe.setEmailAddress(p.getEmailAddress());
        pe.setDateOfBirth(p.getDateOfBirth().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return pe;
    }

    private Person convertToObject(PersonEntity pe) {
        Person to = new Person();
        to.setFirstName(pe.getFirstName());
        to.setLastName(pe.getLastName());
        to.setEmailAddress(pe.getEmailAddress());
        to.setDateOfBirth(java.sql.Date.valueOf(pe.getDateOfBirth()));
        return to;
    }
}
