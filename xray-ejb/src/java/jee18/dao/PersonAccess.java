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
import javax.persistence.NoResultException;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "PersonAccess")
@LocalBean
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

    @Override
    public PersonEntity getByUuid(String uuid) {
        try {
            return em.createNamedQuery("PersonEntity.getPersonEntityByUUID", PersonEntity.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Integer updateByUuid(String uuid, PersonEntity person) {
        return em.createNamedQuery("PersonEntity.updatePersonEntityByUUID", PersonEntity.class)
                .setParameter("uuid", uuid)
                .setParameter("firstName", person.getFirstName())
                .setParameter("lastName", person.getLastName())
                .setParameter("dateOfBirth", person.getDateOfBirth())
                .setParameter("emailAddress", person.getEmailAddress())
                .executeUpdate();
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("PersonEntity.deletePersonEntityByUUID", PersonEntity.class)
                .setParameter("uuid", uuid)
                .executeUpdate();
    }

}
