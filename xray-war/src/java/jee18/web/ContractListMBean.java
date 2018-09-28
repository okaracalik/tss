/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.context.FacesContext;
import jee18.dto.Contract;
import jee18.logic.IContractSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "contractListMBean")
@RequestScoped
public class ContractListMBean {

    @ManagedProperty(value = "#{appMBean}")
    AppMBean app;
    
    @EJB
    private IContractSystem contractSystem;

    private List<Contract> contractList;
  
    public ContractListMBean() {
    }

    public List<Contract> getContractList() {
        if (contractList == null) {
            contractList = contractSystem.listMyContracts(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        }
        return contractList;
    }

}
