/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.Stateless;
import jee18.entities.ContractEntity;
import jee18.entities.PersonEntity;

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

}
