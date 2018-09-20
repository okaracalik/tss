/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import jee18.dao.ContractAccess;
import jee18.dao.PersonAccess;
import jee18.dao.RoleAccess;
import jee18.dao.TimesheetAccess;
import jee18.dao.TimesheetEntryAccess;
import jee18.dto.Person;
import jee18.entities.PersonEntity;
import jee18.logic.IAppSystem;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "AppSystem")
public class AppSystem implements IAppSystem {

    @EJB
    private RoleAccess roleAccess;

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private TimesheetAccess timesheetAccess;

    @EJB
    private TimesheetEntryAccess timesheetEntryAccess;

    @EJB
    private PersonAccess personAccess;

    @Override
    public void generateData() {
        PersonEntity temp;
        String secretary = "SECRETARY";
        String supervisor = "SUPERVISOR";
        String employee = "EMPLOYEE";
        String assistant = "ASSISTANT";

        for (int i = 1; i < 4; i++) {
            try {
                Person se = new Person();
                se.setFirstName(secretary.toLowerCase() + "-" + i);
                se.setLastName("a");
                se.setEmailAddress(se.getFirstName() + "@" + se.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(se), Stream.of(secretary).collect(Collectors.toList()));

                Person su = new Person();
                su.setFirstName(supervisor.toLowerCase() + "-" + i);
                su.setLastName("a");
                su.setEmailAddress(su.getFirstName() + "@" + su.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(su), Stream.of(supervisor).collect(Collectors.toList()));

                Person em = new Person();
                em.setFirstName(employee.toLowerCase() + "-" + i);
                em.setLastName("a");
                em.setEmailAddress(em.getFirstName() + "@" + em.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(em), Stream.of(employee).collect(Collectors.toList()));

                Person as = new Person();
                as.setFirstName(assistant.toLowerCase() + "-" + i);
                as.setLastName("a");
                as.setEmailAddress(as.getFirstName() + "@" + as.getLastName());
                temp = personAccess.createWithRoles(Person.toEntity(as), Stream.of(assistant).collect(Collectors.toList()));
            }
            catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(AppSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void truncateData() {
        timesheetEntryAccess.truncate();
        timesheetAccess.truncate();
        roleAccess.truncate();
        contractAccess.truncate();
        personAccess.truncate();
    }

}
