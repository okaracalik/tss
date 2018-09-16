/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import java.util.Set;
import jee18.dto.Person;

/**
 *
 * @author okaracalik
 */
public interface IPersonSystem {

    public List<Person> list();

    public Person add(Person p, List<String> roles);

    public Person get(String uuid);

    public Integer update(String uuid, Person p);

    public Integer delete(String uuid);

}
