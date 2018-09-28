/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import dto.Person;
import entity.PersonEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author apple
 */
@Remote
public interface PersonLogic {
    public List<Person> getPersonList();
    public PersonEntity createPerson(PersonEntity p);
    
    public void createTestData();
}
