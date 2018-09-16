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
@Entity(name = "Secretary")
@DiscriminatorValue("Secretary")
@NamedQueries({
    @NamedQuery(
            name = "RoleEntity.getSecretaryList",
            query = "SELECT e FROM Secretary e"
    )
    ,
    @NamedQuery(
            name = "RoleEntity.getSecretaryByUUID",
            query = "SELECT e FROM Secretary e WHERE e.uuid = :uuid"
    )
})
public class Secretary extends RoleEntity {

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private ContractEntity contract;

    public Secretary() {
        this(false);
    }

    Secretary(boolean isNew) {
        super(isNew);
    }

    public static Secretary newInstance() {
        return new Secretary(true);
    }

}
