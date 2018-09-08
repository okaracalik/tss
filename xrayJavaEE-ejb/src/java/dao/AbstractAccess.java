/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author apple
 */
public abstract class AbstractAccess {
    @PersistenceContext(unitName = "xrayJavaEE-ejbPU")
    protected EntityManager em;
    
}
