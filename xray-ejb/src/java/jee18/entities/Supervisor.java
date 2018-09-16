/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 *
 * @author okaracalik
 */
@Entity(name = "Supervisor")
@DiscriminatorValue("Supervisor")
public class Supervisor extends RoleEntity {

    @OneToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;
    
    public Supervisor() {
        this(false);
    }

    Supervisor(boolean isNew) {
        super(isNew);
    }

    public static Supervisor newInstance() {
        return new Supervisor(true);
    }

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }
    
    

}
