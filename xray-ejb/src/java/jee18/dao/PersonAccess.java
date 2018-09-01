/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import jee18.entities.PersonEntity;
import javax.ejb.Stateless;

/**
 *
 * @author okaracalik
 */
@Stateless(name="PersonAccess")
public class PersonAccess extends AbstractAccess implements IAccess<PersonEntity> {

    public PersonAccess() {
        super(PersonEntity.class);
    }

    @Override
    public PersonEntity create(PersonEntity person) {
        em.persist(person);
        return person;
    }

    @Override
    public List<PersonEntity> getList() {
        return em.createNamedQuery("PersonEntity.getPersonList", PersonEntity.class).getResultList();
    }

}
