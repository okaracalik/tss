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
import jee18.entities.Assistant;
import jee18.entities.Employee;
import jee18.entities.Secretary;
import jee18.entities.Supervisor;

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

    public PersonEntity createWithRoles(PersonEntity person, List<String> roles) {
        em.persist(person);
        roles.forEach(role -> {
            if (role.contains("SECRETARY")) {
                Secretary secretary = Secretary.newInstance();
                secretary.setPerson(person);
                person.getRoles().add(secretary);
            }
            else if (role.contains("EMPLOYEE")) {
                Employee employee = Employee.newInstance();
                employee.setPerson(person);
                person.getRoles().add(employee);
            }
            else if (role.contains("SUPERVISOR")) {
                Supervisor supervisor = Supervisor.newInstance();
                supervisor.setPerson(person);
                person.getRoles().add(supervisor);
            }
            else if (role.contains("ASSISTANT")) {
                Assistant assistant = Assistant.newInstance();
                assistant.setPerson(person);
                person.getRoles().add(assistant);
            }
            else {
                System.out.println("jee18.dao.PersonAccess.createWithRoles(): no role");
            }
        });
        return person;
    }

    public PersonEntity getByEmailAddress(String emailAddress) {
        try {
            return em.createNamedQuery("PersonEntity.getPersonEntityByEmailAddress", PersonEntity.class)
                    .setParameter("emailAddress", emailAddress)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }
    
    public Integer truncate() {
        return em.createNamedQuery("PersonEntity.truncate", PersonEntity.class).executeUpdate();
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

    private Exception EJBException(String role_does_not_exist) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
