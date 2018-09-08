/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import jee18.entities.enums.Role;

/**
 *
 * @author okaracalik
 */
public class RoleEntity extends AbstractEntity {

    @ManyToOne
    private PersonEntity person;

    @Enumerated(EnumType.STRING)
    private Role role;

}
