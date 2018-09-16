/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author okaracalik
 */
@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "title")
public class RoleEntity extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonEntity person;
    private String title;
    @JoinColumn(name = "contract_id")
    protected ContractEntity contract;

    public RoleEntity() {
        this(false);
    }

    RoleEntity(boolean isNew) {
        super(isNew);
    }

    public static RoleEntity newInstance() {
        return new RoleEntity(true);
    }

    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public ContractEntity getContract() {
        return contract;
    }

    public void setContract(ContractEntity contract) {
        this.contract = contract;
    }

}
