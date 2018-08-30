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
import jee18.logic.TimesheetSystemLocal;

/**
 *
 * @author okaracalik
 */
@ViewScoped
@Named
public class PersonFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private TimesheetSystemLocal timeSheetSystem;

    private Person person;

    public PersonFormMBean() {

    }

    @PostConstruct
    public void init() {
        person = new Person();
    }

    public Person getPerson() {
        return person;
    }

    public void createPerson() {
        try {
            timeSheetSystem.createPerson(person);
            System.out.println(person);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
