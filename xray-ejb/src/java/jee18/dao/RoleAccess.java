/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee18.entities.Assistant;
import jee18.entities.Employee;
import jee18.entities.RoleEntity;
import jee18.entities.Secretary;
import jee18.entities.Supervisor;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "RoleAccess")
@LocalBean
public class RoleAccess extends AbstractAccess implements IAccess<RoleEntity> {

    public RoleAccess() {
        super(RoleEntity.class);
    }

    public List<Secretary> getSecretaryList() {
        return em.createNamedQuery("RoleEntity.getSecretaryList", Secretary.class).getResultList();
    }

    public List<Employee> getEmployeeList() {
        return em.createNamedQuery("RoleEntity.getEmployeeList", Employee.class).getResultList();
    }

    public List<Supervisor> getSupervisorList() {
        return em.createNamedQuery("RoleEntity.getSupervisorList", Supervisor.class).getResultList();
    }

    public List<Assistant> getAssistantList() {
        return em.createNamedQuery("RoleEntity.getAssistantList", Assistant.class).getResultList();
    }

    @Override
    public List<RoleEntity> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RoleEntity create(RoleEntity a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RoleEntity getByUuid(String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer updateByUuid(String uuid, RoleEntity a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
