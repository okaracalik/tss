/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import jee18.entities.AbstractEntity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author okaracalik
 * @param <A>
 */
public class AbstractAccess<A extends AbstractEntity> {
    
    protected Class<A> entityClass;
    
    @PersistenceContext(unitName = "xray-ejbPU")
    protected EntityManager em;
    
    public AbstractAccess(Class<A> entityClass) {
        this.entityClass = entityClass;
    }
    
    protected A getAbstractEntityById(long id) {
        return em.find(entityClass, id);
    }
    
}
