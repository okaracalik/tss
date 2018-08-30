/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.LocalBean;
import jee18.entities.PersonEntity;
import javax.ejb.Stateless;

/**
 *
 * @author okaracalik
 */
@Stateless
@LocalBean
public class PersonAccess extends AbstractAccess implements IAccess {

    public PersonAccess() {
        super(PersonEntity.class);
    }

    public PersonEntity addPerson(PersonEntity person) {
        em.persist(person);
        return person;
    }

    public List<PersonEntity> getPersonList() {
        return em.createNamedQuery("PersonEntity.getPersonList", PersonEntity.class).getResultList();
    }

}
