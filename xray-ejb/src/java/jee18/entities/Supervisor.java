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
@Entity(name = "Supervisor")
@DiscriminatorValue("Supervisor")
public class Supervisor extends RoleEntity {

    public Supervisor() {
        this(false);
    }

    Supervisor(boolean isNew) {
        super(isNew);
    }

    public static Supervisor newInstance() {
        return new Supervisor(true);
    }

}
