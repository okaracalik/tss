/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.NamedEntity;
import javax.persistence.NoResultException;

/**
 *
 * @author apple
 */
public class NamedEntityAccess<T extends NamedEntity> extends AbstractAccess{
    protected Class<T> entityClass;

    public NamedEntityAccess(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected T getNamedEntityById(long id) {
        return em.find(entityClass, id);
    }

    protected T getNamedEntityByUuid(String uuid) {
        try {
            return em.createNamedQuery("NamedEntity.getNamedEntityByUUID", entityClass)
                    .setParameter("uuid", uuid)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    
    
}
