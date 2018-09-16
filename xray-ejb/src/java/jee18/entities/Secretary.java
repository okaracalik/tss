/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author okaracalik
 */
@Entity(name = "Secretary")
@DiscriminatorValue("Secretary")
public class Secretary extends RoleEntity {

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
