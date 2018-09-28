/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author apple
 */
@Entity
public class SecretaryEntity extends RoleEntity {

    private static final long serialVersionUID = 1L;
    

    @ManyToOne
    private ContractEntity contract;

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

    public ContractEntity getContract() {
        return contract;
    }
    public SecretaryEntity() {
        this(false);
    }
     SecretaryEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            
        }
    }
    public static SecretaryEntity newInstance() {
        return new SecretaryEntity(true);
    }
    
}
