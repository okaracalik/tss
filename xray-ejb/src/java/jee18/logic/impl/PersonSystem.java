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
import jee18.logic.ICRUD;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "PersonSystem")
public class PersonSystem extends AbstractTimesheetSystem<Person, PersonEntity> implements ICRUD<Person> {

    public PersonSystem() throws NamingException {
        super("PersonAccess");
    }

    @Override
    protected PersonEntity convertToEntity(Person p) {
        return Person.toEntity(p);
    }

    @Override
    protected Person convertToObject(PersonEntity pe) {
        return Person.toDTO(pe);
    }

}
