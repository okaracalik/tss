/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import jee18.dto.Contract;
import jee18.dto.Role;
import jee18.logic.IContractSystem;
import jee18.logic.IRoleSystem;

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
    @EJB
    private IRoleSystem roleSystem;

    private Contract contract;
    private String uuid;
    private List<Role> secretaries;
    private List<Role> employees;
    private List<Role> supervisors;
    private List<Role> assistants;
    private String secretaryUuids;
    private String employeeUuid;
    private String supervisorUuid;
    private String assistantUuids;
    private final String emailAddress = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
    public ContractFormMBean() {
    }

    @PostConstruct
    public void init() {
        if (uuid == null) {
            contract = new Contract();
        }
        else {
            contract = (Contract) contractSystem.getMyContract(uuid, emailAddress);
            System.out.println(contract);
        }
        secretaries = roleSystem.listSecretary();
        employees = roleSystem.listEmployee();
        supervisors = roleSystem.listSupervisor();
        assistants = roleSystem.listAssistant();
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

    public IRoleSystem getRoleSystem() {
        return roleSystem;
    }

    public List<Role> getSecretaries() {
        return secretaries;
    }

    public List<Role> getEmployees() {
        return employees;
    }

    public List<Role> getSupervisors() {
        return supervisors;
    }

    public List<Role> getAssistants() {
        return assistants;
    }

    public String getSecretaryUuids() {
        return secretaryUuids;
    }

    public void setSecretaryUuids(String secretaryUuids) {
        this.secretaryUuids = secretaryUuids;
    }

    public String getEmployeeUuid() {
        return employeeUuid;
    }

    public void setEmployeeUuid(String employeeUuid) {
        this.employeeUuid = employeeUuid;
    }

    public String getSupervisorUuid() {
        return supervisorUuid;
    }

    public void setSupervisorUuid(String supervisorUuid) {
        this.supervisorUuid = supervisorUuid;
    }

    public String getAssistantUuids() {
        return assistantUuids;
    }

    public void setAssistantUuids(String assistantUuids) {
        this.assistantUuids = assistantUuids;
    }

    public String save() {
        try {
            if (uuid == null) {
                contractSystem.add(contract, Arrays.asList(secretaryUuids.split("\\s*,\\s*")), employeeUuid, supervisorUuid, Arrays.asList(assistantUuids.split("\\s*,\\s*")));
            }
            else {
                contractSystem.update(uuid, contract, emailAddress);
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
            contractSystem.delete(uuid, emailAddress);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String start() {
        try {
            contractSystem.setStatusToStarted(uuid, emailAddress);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String terminate() {
        try {
            contractSystem.setStatusToTerminated(uuid, emailAddress);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

    public String archive() {
        try {
            contractSystem.setStatusToArchived(uuid, emailAddress);
            return "/contract/contract-list.xhtml?faces-redirect=true";
        }
        catch (Exception e) {
            System.out.println(e.toString());
            return "error";
        }
    }

}
