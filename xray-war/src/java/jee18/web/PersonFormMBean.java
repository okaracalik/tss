/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import jee18.dto.Person;
import jee18.logic.IPersonSystem;

/**
 *
 * @author okaracalik
 */
@ViewScoped
@Named
public class PersonFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB(beanName = "PersonSystem")
    private IPersonSystem personSystem;

    private Person person;
    private String uuid;
    private String roles;
    
    public PersonFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            person = new Person();
        }
        else {
            person = (Person) personSystem.get(uuid);
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
    
    public String save() {
        try {
            System.out.print(roles);
            if (uuid == null) {
                personSystem.add(person, Arrays.asList(roles.split("\\s*,\\s*")));
            }
            else {
                personSystem.update(uuid, person);
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
            personSystem.delete(uuid);
            return "/person/person-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

}
