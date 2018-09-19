/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.ContractAccess;
import jee18.dto.Contract;
import jee18.dto.Timesheet;
import jee18.entities.ContractEntity;
import jee18.entities.enums.ContractStatus;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.IContractSystem;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "ContractSystem")
public class ContractSystem extends AbstractTimesheetSystem<Contract, ContractEntity> implements IContractSystem {

    @EJB
    private ContractAccess contractAccess;

    public ContractSystem() throws NamingException {
        super("ContractAccess");
    }

    @Override
    protected ContractEntity convertToEntity(Contract c) {
        return Contract.toEntity(c);
    }

    @Override
    protected Contract convertToObject(ContractEntity ce) {
        return Contract.toDTO(ce);
    }

    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public List<Contract> list() {
        return super.getList();
    }

    @RolesAllowed("EMPLOYEE")
    @Override
    public List<Contract> listMyContracts(String employeeUuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Contract add(Contract c, List<String> secretaryUuids, String employeeUuid, String supervisorUuid, List<String> assistantUuids) {
        return Contract.toDTO(contractAccess.createWithPersons(Contract.toEntity(c), secretaryUuids, employeeUuid, supervisorUuid, assistantUuids));
    }

    @RolesAllowed({"SECRETARY", "SUPERVISOR", "ASSISTANT", "EMPLOYEE"})
    @Override
    public Contract get(String uuid) {
        return super.getByUuid(uuid);
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer update(String uuid, Contract c) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            return super.updateByUuid(uuid, c);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer delete(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            return super.deleteByUuid(uuid);
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer setStatusToStarted(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.PREPARED) {
            HashMap<LocalDate, LocalDate> dates = DateTimeUtil.findTimesheetDates(DateTimeUtil.convertDateToLocalDate(contract.getStartDate()), DateTimeUtil.convertDateToLocalDate(contract.getEndDate()), contract.getFrequency());
            Set<Timesheet> ts = dates.entrySet().stream().map(x -> {
                Timesheet t = new Timesheet();
                t.setStartDate(DateTimeUtil.convertLocalDateToDate(x.getKey()));
                t.setEndDate(DateTimeUtil.convertLocalDateToDate(x.getValue()));
                return t;
            }).collect(Collectors.toSet());

            return contractAccess.start(uuid, ts.stream().map(x -> Timesheet.toEntity(x)).collect(Collectors.toSet()));
        }
        else {
            throw new EJBException("Contract must be in prepared status.");
        }

    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer setStatusToTerminated(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.STARTED) {
            return contractAccess.terminate(uuid);
        }
        else {
            throw new EJBException("Contract must be in started status.");
        }
    }

    @RolesAllowed({"SUPERVISOR", "ASSISTANT"})
    @Override
    public Integer setStatusToArchived(String uuid) {
        Contract contract = super.getByUuid(uuid);
        if (contract.getStatus() == ContractStatus.STARTED || contract.getStatus() == ContractStatus.TERMINATED) {
            return contractAccess.archive(uuid);
        }
        else {
            throw new EJBException("Contract must be either started or terminated.");
        }
    }

    @RolesAllowed("SECRETARY")
    @Override
    public void print() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
