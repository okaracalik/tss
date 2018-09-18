/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
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
        try {
            return Person.toEntity(p);
        }
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PersonSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    protected Person convertToObject(PersonEntity pe) {
        return Person.toDTO(pe);
    }

    @RolesAllowed("SECRETARY")
    @Override
    public List<Person> list() {
        return super.getList();
    }

    @RolesAllowed("SECRETARY")
    @Override
    public Person add(Person p, List<String> roles) {
        System.out.print(roles);
        try {
            return Person.toDTO(personAccess.createWithRoles(Person.toEntity(p), roles));
        }
        catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PersonSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @RolesAllowed("SECRETARY")
    @Override
    public Person get(String uuid) {
        return super.getByUuid(uuid);
    }

    @RolesAllowed("SECRETARY")
    @Override
    public Integer update(String uuid, Person p) {
        return super.updateByUuid(uuid, p);
    }

    @RolesAllowed("SECRETARY")
    @Override
    public Integer delete(String uuid) {
        return super.deleteByUuid(uuid);
    }

}
