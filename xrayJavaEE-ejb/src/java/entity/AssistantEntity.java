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
public class AssistantEntity extends RoleEntity {

    private static final long serialVersionUID = 1L;
    public AssistantEntity() {
        this(false);
    }
     AssistantEntity(boolean isNew) {
        super(isNew);
        if (isNew) {
            
        }
    }
    public static AssistantEntity newInstance() {
        return new AssistantEntity(true);
    }
    
}
