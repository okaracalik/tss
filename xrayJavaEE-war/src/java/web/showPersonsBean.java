/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dto.Person;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import logic.PersonLogic;

/**
 *
 * @author apple
 */
@Named
@RequestScoped
public class showPersonsBean {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @EJB
    private PersonLogic personLogic;
    
    @EJB
    private List<Person> persons;
    public List<Person> setPerons(){
        return persons=personLogic.getPersonList();
    }
}
