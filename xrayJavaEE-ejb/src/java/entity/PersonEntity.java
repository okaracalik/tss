/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author apple
 */
@NamedQuery(
            name = "PersonEntity.getPersonList",
            query = "SELECT p FROM PersonEntity p ORDER BY p.name"
)
@NamedQuery(
            name = "PersonEntity.getRoleNames",
            query = "SELECT role.rtype FROM PersonEntity p, IN(p.roles) role"
            + " WHERE p.id = :personId ORDER BY role.rtype"
)
@Entity
@Table(name = "PERSON")

public class PersonEntity extends NamedEntity {

    private static final long serialVersionUID = 1L;

    


    private String firstName;

    private String lastName;
    private LocalDate dateOfBirth;
    private String emailAddress;
    

   

    @OneToMany(mappedBy = "person")
    private Set<RoleEntity> roles;

    public Set<RoleEntity> getRoles() {
        return roles;
    }
    
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





    
}
