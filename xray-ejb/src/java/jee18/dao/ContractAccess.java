/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee18.entities.ContractEntity;

/**
 *
 * @author okaracalik
 */
@Stateless(name="ContractAccess")
public class ContractAccess extends AbstractAccess{

    public ContractAccess() {
        super(ContractEntity.class);
    }
    
    public ContractEntity addContract(ContractEntity contract) {
        em.persist(contract);
        return contract;
    }
    
    public List<ContractEntity> getContractList() {
        return em.createNamedQuery("ContractEntity.getContractList", ContractEntity.class).getResultList();
    }

//    @Override
//    public ContractEntity create(ContractEntity contract) {
//        em.persist(contract);
//        return contract;
//    }
//
//    @Override
//    public List<ContractEntity> getList() {
//        return em.createNamedQuery("ContractEntity.getContractList", ContractEntity.class).getResultList();
//    }

}
