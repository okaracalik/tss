/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import jee18.dto.Person;
import jee18.logic.ITimesheetManagementSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "personListMBean")
@RequestScoped
public class PersonListMBean {

    @EJB(beanName = "PersonSystem")
    private ITimesheetManagementSystem personSystem;

    private List<Person> personList;

    public PersonListMBean() {
    }

    public List<Person> getPersonList() {
        if (personList == null) {
            personList = personSystem.getList();
        }
        return personList;
    }

}
