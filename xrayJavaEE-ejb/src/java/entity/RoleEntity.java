/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**
 *
 * @author apple
 */
@Entity
@Table(name="ROLE")
public class RoleEntity extends NamedEntity {

    private static final long serialVersionUID = 1L;
    
    private String rtype;

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }
    @ManyToOne
    private PersonEntity person;

    
    public PersonEntity getPerson() {
        return person;
    }

    public void setPerson(PersonEntity person) {
        this.person = person;
    }

    public RoleEntity() {
        this(false);
    }
    
    RoleEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            
        }
    }
    public static RoleEntity newInstance() {
        return new RoleEntity(true);
    }
    
}
