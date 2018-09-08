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
import jee18.logic.ITimesheetManagementSystem;

/**
 *
 * @author okaracalik
 */
@ViewScoped
@Named
public class PersonFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB(beanName = "PersonSystem")
    private ITimesheetManagementSystem personSystem;

    private Person person;
    private String uuid;

    public PersonFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            person = new Person();
        }
        else {
            person = (Person) personSystem.getByUuid(uuid);
        }
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

    public String save() {
        try {
            if (uuid == null) {
                personSystem.create(person);
            }
            else {
                personSystem.updateByUuid(uuid, person);
            }
            return "/person/person-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String delete() {
        try {
            personSystem.deleteByUuid(uuid);
            return "/person/person-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

}
