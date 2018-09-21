/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jee18.dto.Contract;
import jee18.logic.IContractSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "contractFormMBean")
@ViewScoped
public class ContractFormMBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @EJB
    private IContractSystem contractSystem;

    private Contract contract;
    private String uuid;

    public ContractFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            contract = new Contract();
        }
        else {
            contract = (Contract) contractSystem.get(uuid);
        }
        System.out.print(contract);
    }

    public Contract getContract() {
        return contract;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public String save() {
        try {
            if (uuid == null) {
                contractSystem.add(contract);
            }
            else {
                contractSystem.update(uuid, contract);
            }
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String delete() {
        try {
            contractSystem.delete(uuid);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String start() {
        try {
            contractSystem.setStatusToStarted(uuid);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String terminate() {
        try {
            contractSystem.setStatusToTerminated(uuid);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String archive() {
        try {
            contractSystem.setStatusToArchived(uuid);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

}
