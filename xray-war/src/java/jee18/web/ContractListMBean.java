/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import jee18.dto.Contract;
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "contractListMBean")
@RequestScoped
public class ContractListMBean {

    @EJB(beanName = "ContractSystem")
    private ITimesheetSystem contractSystem;

    private List<Contract> contractList;

    public ContractListMBean() {
    }

    public List<Contract> getContractList() {
        if (contractList == null) {
            contractList = contractSystem.getList();
        }
        return contractList;
    }
    
}
