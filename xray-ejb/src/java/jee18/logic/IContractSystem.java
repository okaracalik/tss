/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import jee18.dto.Contract;

/**
 *
 * @author okaracalik
 */
public interface IContractSystem {

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    public List<Contract> list();

    // FIXME: employee, may be supervisor, assistant
    // -EMPLOYEE ***
    public List<Contract> listMyContracts(String employeeUuid);
    
    // -SUPERVISOR
    // -ASSISTANT
    public Contract add(Contract c, List<String> secretartUuids, String employeeUuid, String supervisorUuid, List<String> assistantUuids);

    // -SECRETARY
    // -SUPERVISOR
    // -ASSISTANT
    // TASK: stats
    public Contract get(String uuid);
    
    // FIXME: employee, may be supervisor, assistant
    // -EMPLOYEE ***
    // public Contract getMyContract(String uuid, String ownerUuid);

    // FIXME: owner
    // -SUPERVISOR
    // -ASSISTANT
    // RULE: if prepared
    public Integer update(String uuid, Contract c);

    // FIXME: employee, may be supervisor, assistant
    // -SUPERVISOR
    // -ASSISTANT
    // RULE: if prepared
    // TASK: delete timesheets in progress
    public Integer delete(String uuid);

    // FIXME: owner
    // -SUPERVISOR
    // -ASSISTANT
    // TASK: create timesheets
    public Integer setStatusToStarted(String uuid);

    // FIXME: owner
    // -SUPERVISOR
    // -ASSISTANT
    // TASK: set termination date
    public Integer setStatusToTerminated(String uuid);

    // FIXME: this is called when all timesheets are archived.
    public Integer setStatusToArchived(String uuid);

    // -SECRETARY
    public void print();

}
