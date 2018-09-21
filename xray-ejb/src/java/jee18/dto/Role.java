/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dto;

import java.io.Serializable;
import jee18.entities.RoleEntity;

/**
 *
 * @author okaracalik
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 3419675164523830829L;

    private String uuid;
    private String title;
    private Person person;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Role{" + "uuid=" + uuid + ", title=" + title + ", person=" + person + '}';
    }

    public static RoleEntity toEntity(Role dto) {
        RoleEntity e = RoleEntity.newInstance();
        e.setTitle(dto.getTitle());
        e.setPerson(Person.toEntity(dto.getPerson()));
        return e;
    }

    public static Role toDTO(RoleEntity e) {
        Role dto = new Role();
        dto.setUuid(e.getUuid());
        dto.setTitle(e.getTitle());
        dto.setPerson(Person.toDTO(e.getPerson()));
        return dto;
    }

}
