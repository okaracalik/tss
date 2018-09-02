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
import jee18.logic.ITimesheetSystem;

/**
 *
 * @author okaracalik
 */
@Named(value = "contractFormMBean")
@ViewScoped
public class ContractFormMBean implements Serializable {


    private static final long serialVersionUID = 1L;

    @EJB(beanName = "ContractSystem")
    private ITimesheetSystem contractSystem;

    private Contract contract;
    private String uuid;

    public ContractFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            contract = new Contract();
        }
        contract = (Contract) contractSystem.getByUuid(uuid);
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

    public void save() {
        try {
            if (uuid == null) {
                contractSystem.create(contract);
            }
            else {
                contractSystem.updateByUuid(uuid, contract);
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void delete() {
        try {
            contractSystem.deleteByUuid(uuid);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
