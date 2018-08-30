/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author okaracalik
 */
@Entity
@Table(name = "persons")
@NamedQueries({
    @NamedQuery(
            name = "PersonEntity.getPersonList",
            query = "SELECT p FROM PersonEntity p"
    )
})
public class PersonEntity extends AbstractEntity {

    private static final long serialVersionUID = 8164978510161170908L;
    // TODO: role 
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String emailAddress;

    public PersonEntity() {
        this(false);
    }

    PersonEntity(boolean isNew) {
        super(isNew);
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

    @Override
    public String toString() {
        return "PersonEntity{" + "firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", emailAddress=" + emailAddress + '}';
    }

}
