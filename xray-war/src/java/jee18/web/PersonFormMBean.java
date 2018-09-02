/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee18.dto.Person;
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@ViewScoped
@Named
public class PersonFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB(beanName = "PersonSystem")
    private ITimesheetSystem personSystem;

    private Person person;
    private String uuid;

    public PersonFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            person = new Person();
        }
        person = (Person) personSystem.getByUuid(uuid);
    }

    public Person getPerson() {
        return person;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void save() {
        try {
            if (uuid == null) {
                personSystem.create(person);
            }
            else {
                personSystem.updateByUuid(uuid, person);
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void delete() {
        try {
            personSystem.deleteByUuid(uuid);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
