/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import jee18.entities.Assistant;
import jee18.entities.ContractEntity;
import jee18.entities.Employee;
import jee18.entities.PersonEntity;
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

    @EJB
    private PersonAccess personAccess;

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

    public Integer start(String uuid, Set<TimesheetEntity> tes, String emailAddress) {
        ContractEntity e = getMyContractByUuid(uuid, emailAddress);
        if (e != null) {
            Integer rows = em.createNamedQuery("ContractEntity.updateContractEntityStatusByUUID", ContractEntity.class)
                    .setParameter("uuid", e.getUuid())
                    .setParameter("status", ContractStatus.STARTED)
                    .setParameter("terminationDate", e.getTerminationDate())
                    .executeUpdate();
            tes.forEach(te -> {
                te.setContract(e);
                e.getTimesheets().add(te);
            });
            return rows;
        }
        else {
            throw new EJBException("User is not authorized on contract.");
        }
    }

    public Integer terminate(String uuid, String emailAddress) {
        ContractEntity e = getMyContractByUuid(uuid, emailAddress);
        if (e != null) {
            Integer rows = em.createNamedQuery("ContractEntity.updateContractEntityStatusByUUID", ContractEntity.class)
                    .setParameter("uuid", e.getUuid())
                    .setParameter("status", ContractStatus.TERMINATED)
                    .setParameter("terminationDate", LocalDate.now())
                    .executeUpdate();
            em.createNamedQuery("TimesheetEntity.deleteTimesheetEntityInProgressByContractId", TimesheetEntity.class)
                    .setParameter("contract", e)
                    .setParameter("status", TimesheetStatus.IN_PROGRESS)
                    .executeUpdate();
            return rows;
        }
        else {
            throw new EJBException("User is not authorized on contract.");
        }
    }

    public Integer archive(String uuid, String emailAddress) {
        ContractEntity e = getMyContractByUuid(uuid, emailAddress);
        if (e != null) {
            Integer rows = em.createNamedQuery("ContractEntity.updateContractEntityStatusByUUID", ContractEntity.class)
                    .setParameter("uuid", e.getUuid())
                    .setParameter("status", ContractStatus.ARCHIVED)
                    .setParameter("terminationDate", e.getTerminationDate())
                    .executeUpdate();
            return rows;
        }
        else {
            throw new EJBException("User is not authorized on contract.");
        }
    }

    public List<ContractEntity> getMyContractList(String emailAddress) {
        System.out.println("jee18.dao.ContractAccess.getMyList()");
        return findMyContracts(getList(), personAccess.getByEmailAddress(emailAddress));
    }

    public ContractEntity getMyContractByUuid(String uuid, String emailAddress) {
        return findMyContracts(Arrays.asList(getByUuid(uuid)), personAccess.getByEmailAddress(emailAddress)).get(0);
    }

    public Integer updateMyContractByUuid(String uuid, ContractEntity c, String emailAddress) {
        ContractEntity ce = getMyContractByUuid(uuid, emailAddress);
        if (ce != null) {
            return updateByUuid(uuid, c);
        }
        else {
            throw new EJBException("User is not authorized on contract.");
        }
    }

    public Integer deleteMyContractByUuid(String uuid, String emailAddress) {
        ContractEntity ce = getMyContractByUuid(uuid, emailAddress);
        if (ce != null) {
            return deleteByUuid(uuid);
        }
        else {
            throw new EJBException("User is not authorized on contract.");
        }
    }

    public static List<ContractEntity> findMyContracts(List<ContractEntity> contracts, PersonEntity person) {
        System.out.println("jee18.dao.ContractAccess.findMyContracts()");
        List<String> uuids = person.getRoleUuids();
        List<ContractEntity> employee = contracts.stream().filter(c -> uuids.contains(c.getEmployee().getUuid())).collect(Collectors.toList());
        List<ContractEntity> supervisor = contracts.stream().filter(c -> uuids.contains(c.getSupervisor().getUuid())).collect(Collectors.toList());
        List<ContractEntity> assistant = contracts.stream().filter(c -> c.getAssistants().stream().anyMatch(a -> uuids.contains(a.getUuid()))).collect(Collectors.toList());
        List<ContractEntity> secretary = contracts.stream().filter(c -> c.getSecretaries().stream().anyMatch(a -> uuids.contains(a.getUuid()))).collect(Collectors.toList());
        employee.addAll(supervisor);
        employee.addAll(assistant);
        employee.addAll(secretary);
        return employee;
    }

    public Integer truncate() {
        return em.createNamedQuery("ContractEntity.truncate", ContractEntity.class).executeUpdate();
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
        if (e.getStatus() == ContractStatus.PREPARED) {
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
            return rows;
        }
        else {
            return 0;
        }
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("ContractEntity.deleteContractEntityByUUID", ContractEntity.class)
                .setParameter("uuid", uuid)
                .executeUpdate();
    }

}
