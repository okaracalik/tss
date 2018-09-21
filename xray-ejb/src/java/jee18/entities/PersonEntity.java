/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author okaracalik
 */
@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "persons")
@NamedQueries({
    @NamedQuery(
            name = "PersonEntity.getPersonList",
            query = "SELECT e FROM PersonEntity e"
    )
    ,
    @NamedQuery(
            name = "PersonEntity.getPersonEntityByUUID",
            query = "SELECT e FROM PersonEntity e WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "PersonEntity.updatePersonEntityByUUID",
            query = "UPDATE PersonEntity e SET e.firstName = :firstName, e.lastName = :lastName, e.dateOfBirth = :dateOfBirth, e.emailAddress = :emailAddress WHERE e.uuid = :uuid"
    )
    ,
    @NamedQuery(
            name = "PersonEntity.deletePersonEntityByUUID",
            query = "DELETE FROM PersonEntity e WHERE e.uuid = :uuid"
    )
})
public class PersonEntity extends AbstractEntity {

    private static final long serialVersionUID = 8164978510161170908L;

    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String emailAddress;
    private String state;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Set<RoleEntity> roles;

    public PersonEntity() {
        this(false);
    }

    PersonEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            roles = new HashSet<>();
        }
    }

    public static PersonEntity newInstance() {
        return new PersonEntity(true);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "PersonEntity{" + "firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", emailAddress=" + emailAddress + ", roles=" + roles + '}';
    }
    
    public void addRoles(RoleEntity r) {
        
    }

}
