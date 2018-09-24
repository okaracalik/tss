/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.Date;
import java.util.List;
import jee18.dto.Contract;
import jee18.dto.Holiday;

/**
 *
 * @author okaracalik
 */
public interface IContractSystem {

    public List<Contract> list();

    public Contract add(Contract c);

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
      
    public List<Holiday> calculatePublicHolidaysInPeriod(String uuid,Date startDate,Date endDate) ; 
    
    public Double calculateHoursDue(String uuid);
}
