/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import javax.ejb.LocalBean;
import jee18.dto.Person;

/**
 *
 * @author okaracalik
 */
@LocalBean
public interface TimesheetSystemLocal {
    
    public List<Person> getPersonList();
    
    public Person createPerson(Person p);
    
}
