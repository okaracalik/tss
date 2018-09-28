/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author apple
 */
@Entity

public class SupervisorEntity extends RoleEntity {

    private static final long serialVersionUID = 1L;
        public SupervisorEntity() {
        this(false);
    }
     SupervisorEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            
        }
    }
    public static SupervisorEntity newInstance() {
        return new SupervisorEntity(true);
    }
    
}
