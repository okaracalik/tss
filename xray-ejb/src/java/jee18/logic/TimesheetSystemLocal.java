/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import javax.ejb.LocalBean;
import jee18.dto.Contract;
import jee18.dto.Timesheet;
import jee18.dto.TimesheetEntry;

/**
 *
 * @author okaracalik
 */
@LocalBean
public interface TimesheetSystemLocal {
    
    public List<Contract> getContractList();
    
    public Contract createContract(Contract c);
    
    public List<Timesheet> getTimesheetList();
    
    public Timesheet createTimesheet(Timesheet t);
    
    public List<TimesheetEntry> getTimesheetEntryList();
    
    public TimesheetEntry createTimesheetEntry(TimesheetEntry te);
    
}
