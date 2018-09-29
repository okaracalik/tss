/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import java.util.HashMap;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jee18.dto.Contract;
import jee18.logic.IContractSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "contractStatsMBean")
@ViewScoped
public class ContractStatsMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IContractSystem contractSystem;

    private Contract contract;
    private String uuid;

    private final String emailAddress = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    private HashMap<String, Double> statistics;

    @PostConstruct
    public void init() {
        if (uuid != null) {
            contract = (Contract) contractSystem.getMyContract(uuid, emailAddress);
            statistics = contractSystem.calculateStatistics(uuid, emailAddress);
            System.out.println(statistics);
        }
    }

    public ContractStatsMBean() {
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public HashMap<String, Double> getStatistics() {
        return statistics;
    }

    public void setStatistics(HashMap<String, Double> statistics) {
        this.statistics = statistics;
    }

}
