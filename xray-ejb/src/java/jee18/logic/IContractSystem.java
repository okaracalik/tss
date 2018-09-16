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

    public List<Contract> list();

    public Contract add(Contract c, List<String> secretartUuids, String employeeUuid, String supervisorUuid, List<String> assistantUuids);

    // TASK: stats
    public Contract get(String uuid);

    // RULE: if prepared
    public Integer update(String uuid, Contract c);

    // RULE: if prepared
    // TASK: delete timesheets in progress
    public Integer delete(String uuid);

    // TASK: create timesheets
    public Integer setStatusToStarted(String uuid);

    // TASK: set termination date
    public Integer setStatusToTerminated(String uuid);

    public Integer setStatusToArchived(String uuid);
    
    public void print();
    
}
