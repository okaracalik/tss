/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.List;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.PersonAccess;
import jee18.dto.Person;
import jee18.entities.PersonEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.IPersonSystem;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "PersonSystem")
public class PersonSystem extends AbstractTimesheetSystem<Person, PersonEntity> implements IPersonSystem {

    @EJB
    private PersonAccess personAccess;

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

    @Override
    public List<Person> list() {
        return super.getList();
    }

    @Override
    public Person add(Person p, List<String> roles) {
        System.out.print(roles);
        return Person.toDTO(personAccess.createWithRoles(Person.toEntity(p), roles));
    }

    @Override
    public Person get(String uuid) {
        return super.getByUuid(uuid);
    }

    @Override
    public Integer update(String uuid, Person p) {
        return super.updateByUuid(uuid, p);
    }

    @Override
    public Integer delete(String uuid) {
        return super.deleteByUuid(uuid);
    }

}
