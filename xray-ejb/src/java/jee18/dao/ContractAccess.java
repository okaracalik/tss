/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import jee18.entities.Assistant;
import jee18.entities.ContractEntity;
import jee18.entities.Employee;
import jee18.entities.Secretary;
import jee18.entities.Supervisor;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.ContractStatus;
import jee18.entities.enums.TimesheetStatus;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "ContractAccess")
@LocalBean
public class ContractAccess extends AbstractAccess implements IAccess<ContractEntity> {

    public ContractAccess() {
        super(ContractEntity.class);
    }

    public ContractEntity createWithPersons(ContractEntity contract, List<String> secretaryUuids, String employeeUuid, String supervisorUuid, List<String> assistantUuids) {
        em.persist(contract);
        secretaryUuids.forEach(uuid -> {
            Secretary secretary = em.createNamedQuery("RoleEntity.getSecretaryByUUID", Secretary.class).setParameter("uuid", uuid).getSingleResult();
            secretary.setContract(contract);
            contract.getSecretaries().add(secretary);
        });
        Employee employee = em.createNamedQuery("RoleEntity.getEmployeeByUUID", Employee.class).setParameter("uuid", employeeUuid).getSingleResult();
        employee.setContract(contract);
        contract.setEmployee(employee);
        Supervisor supervisor = em.createNamedQuery("RoleEntity.getSupervisorByUUID", Supervisor.class).setParameter("uuid", supervisorUuid).getSingleResult();
        supervisor.setContract(contract);
        contract.setSupervisor(supervisor);
        assistantUuids.forEach(uuid -> {
            Assistant assistant = em.createNamedQuery("RoleEntity.getAssistantByUUID", Assistant.class).setParameter("uuid", uuid).getSingleResult();
            assistant.setContract(contract);
            contract.getAssistants().add(assistant);
        });
        return contract;
    }

    @Override
    public ContractEntity create(ContractEntity contract) {
        em.persist(contract);
        return contract;
    }

    @Override
    public List<ContractEntity> getList() {
        return em.createNamedQuery("ContractEntity.getContractList", ContractEntity.class).getResultList();
    }

    @Override
    public ContractEntity getByUuid(String uuid) {
        try {
            return em.createNamedQuery("ContractEntity.getContractEntityByUUID", ContractEntity.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Integer updateByUuid(String uuid, ContractEntity contract) {
        ContractEntity e = getByUuid(uuid);
        Integer rows = em.createNamedQuery("ContractEntity.updateContractEntityByUUID", ContractEntity.class)
                .setParameter("uuid", uuid)
                .setParameter("status", contract.getStatus())
                .setParameter("name", contract.getName())
                .setParameter("startDate", contract.getStartDate())
                .setParameter("endDate", contract.getEndDate())
                .setParameter("frequency", contract.getFrequency())
                .setParameter("terminationDate", contract.getTerminationDate())
                .setParameter("hoursPerWeek", contract.getHoursPerWeek())
                .setParameter("vacationHours", contract.getVacationHours())
                .setParameter("hoursDue", contract.getHoursDue())
                .setParameter("workingDaysPerWeek", contract.getWorkingDaysPerWeek())
                .setParameter("vacationDaysPerYear", contract.getVacationDaysPerYear())
                .executeUpdate();
        if (contract.getStatus() == ContractStatus.STARTED) {
            contract.getTimesheets().forEach((te) -> {
                e.addTimesheets(te);
            });
        }
        else if (contract.getStatus() == ContractStatus.TERMINATED) {
            em.createNamedQuery("TimesheetEntity.deleteTimesheetEntityInProgressByContractId", TimesheetEntity.class)
                    .setParameter("contract", e)
                    .setParameter("status", TimesheetStatus.IN_PROGRESS)
                    .executeUpdate();
        }
        return rows;
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("ContractEntity.deleteContractEntityByUUID", ContractEntity.class)
                .setParameter("uuid", uuid)
                .executeUpdate();
    }

}
