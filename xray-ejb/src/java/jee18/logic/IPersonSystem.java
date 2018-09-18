/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import jee18.dto.Person;

/**
 *
 * @author okaracalik
 */
public interface IPersonSystem {

    // SECRETARY
    public List<Person> list();

    // SECRETARY
    public Person add(Person p, List<String> roles);

    // SECRETARY
    public Person get(String uuid);

    // SECRETARY
    public Integer update(String uuid, Person p);

    // SECRETARY
    public Integer delete(String uuid);

}
