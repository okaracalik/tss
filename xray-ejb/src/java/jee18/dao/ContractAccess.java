/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import jee18.entities.ContractEntity;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "ContractAccess")
public class ContractAccess extends AbstractAccess implements IAccess<ContractEntity> {

    public ContractAccess() {
        super(ContractEntity.class);
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
        return em.createNamedQuery("ContractEntity.updateContractEntityByUUID", ContractEntity.class)
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
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return em.createNamedQuery("ContractEntity.deleteContractEntityByUUID", ContractEntity.class)
                .setParameter("uuid", uuid)
                .executeUpdate();
    }

}
