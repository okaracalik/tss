/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author apple
 */
@Entity
public class ContractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String uuid;
    
    private ContractStatus status;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private TimesheetFrequency frequency;
    
    
    //@OneToMany(mappedBy = "contract")
    private List<SecretaryEntity> secretaryEntitys;
    //@OneToOne(mappedBy = "contract")
    private EmployeeEntity employee;
    
    @OneToOne
    private SupervisorEntity supervisor;
    
    @OneToMany
    private List<AssistantEntity> assitants;

    
    
    public List<SecretaryEntity> getSecretaryEntitys() {
        return secretaryEntitys;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public SupervisorEntity getSupervisor() {
        return supervisor;
    }

    public List<AssistantEntity> getAssitants() {
        return assitants;
    }


    
    public String getUuid() {
        return uuid;
    }
    
    public ContractEntity() {
        this(false);
    }

    ContractEntity(boolean isNew) {
        if (isNew) {
            uuid = UUID.randomUUID().toString();
        }
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
           if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (uuid == null) {
            throw new IllegalStateException("uuid not set");
        }
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final ContractEntity other = (ContractEntity) object;
        if (other.uuid == null) {
            throw new IllegalStateException("other.uuid not set");
        }
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + id;
    }
    
}
