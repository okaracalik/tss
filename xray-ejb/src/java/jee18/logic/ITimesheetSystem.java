/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import jee18.dto.Timesheet;

/**
 *
 * @author okaracalik
 */
public interface ITimesheetSystem {

    // FIXME: employee, may be supervisor, assistant
    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    // -EMPLOYEE ***
    public List<Timesheet> listMyTimesheets(String emailAddress);

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    // -EMPLOYEE ***
     public Timesheet getMyTimesheet(String uuid, String emailAddress);

    // -EMPLOYEE ***
    // RULE: if not archived
    public Integer signAsEmployee(String uuid, String emailAddress);

    // -EMPLOYEE ***
    // TASK: setStatusToInProgress
    // RULE: if not archived
    public Integer revokeEmployeeSignature(String uuid, String emailAddress);

    // -SUPERVISOR ***
    // RULE: if signed by employee
    // RULE: if not archived
    public Integer signAsSupervisor(String uuid, String emailAddress);

    // -SUPERVISOR ***
    // TASK: setStatusToInProgress
    // RULE: if not archived
    public Integer requestChanges(String uuid, String emailAddress);

    // -SECRETARY
    // RULE: if signed by supervisor
    public Integer setStatusToArchived(String uuid, String emailAddress);

    // -SECRETARY
    public void print();

}
