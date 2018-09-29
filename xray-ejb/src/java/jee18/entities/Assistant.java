/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author okaracalik
 */
@Entity(name = "Assistant")
@DiscriminatorValue("Assistant")
@NamedQueries({
    @NamedQuery(
            name = "RoleEntity.getAssistantList",
            query = "SELECT e FROM Assistant e WHERE e.contract IS NULL"
    )
    ,
    @NamedQuery(
            name = "RoleEntity.getAssistantByUUID",
            query = "SELECT e FROM Assistant e WHERE e.uuid = :uuid"
    )
})
public class Assistant extends RoleEntity {

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;

    public Assistant() {
        this(false);
    }

    Assistant(boolean isNew) {
        super(isNew);
    }

    public static Assistant newInstance() {
        return new Assistant(true);
    }

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

}
