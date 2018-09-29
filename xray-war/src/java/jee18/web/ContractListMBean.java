/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final SimpleDateFormat df = new SimpleDateFormat("d MMM ''yy");

    public ContractListMBean() {
    }

    public List<Contract> getContractList() {
        if (contractList == null) {
            contractList = contractSystem.listMyContracts(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        }
        return contractList;
    }

    public String formatDate(Date date) throws ParseException {
        if (date == null) {
            return "-";
        }
        return df.format(date);
    }

}
