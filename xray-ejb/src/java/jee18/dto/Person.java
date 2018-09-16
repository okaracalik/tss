/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import java.util.Date;
import jee18.entities.PersonEntity;
import jee18.utils.DateTimeUtil;

/**
 *
 * @author okaracalik
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 3419675164523830833L;

    private String uuid;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String emailAddress;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
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
        return "Person{" + "firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth=" + dateOfBirth + ", emailAddress=" + emailAddress + '}';
    }
    
    public static jee18.entities.PersonEntity toEntity(Person dto) {
        jee18.entities.PersonEntity e = jee18.entities.PersonEntity.newInstance();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmailAddress(dto.getEmailAddress());
        e.setDateOfBirth(DateTimeUtil.convertDateToLocalDate(dto.getDateOfBirth()));
        return e;
    }
    
    public static Person toDTO(PersonEntity e) {
        Person dto = new Person();
        dto.setUuid(e.getUuid());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmailAddress(e.getEmailAddress());
        dto.setDateOfBirth(DateTimeUtil.convertLocalDateToDate(e.getDateOfBirth()));
        return dto;
    }

}
