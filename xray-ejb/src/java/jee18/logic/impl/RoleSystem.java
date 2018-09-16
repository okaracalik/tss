/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.RoleAccess;
import jee18.dto.Role;
import jee18.entities.RoleEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.IRoleSystem;

@Stateless(name = "RoleSystem")
public class RoleSystem extends AbstractTimesheetSystem<Role, RoleEntity> implements IRoleSystem {

    @EJB
    private RoleAccess roleAccess;

    public RoleSystem() throws NamingException {
        super("RoleAccess");
    }

    @Override
    protected RoleEntity convertToEntity(Role a) {
        return Role.toEntity(a);
    }

    @Override
    protected Role convertToObject(RoleEntity b) {
        return Role.toDTO(b);
    }

    @Override
    public List<Role> listSecretary() {
        return roleAccess.getSecretaryList().stream().map(x -> Role.toDTO(x)).collect(Collectors.toList());
    }

    @Override
    public List<Role> listEmployee() {
        return roleAccess.getEmployeeList().stream().map(x -> Role.toDTO(x)).collect(Collectors.toList());
    }

    @Override
    public List<Role> listSupervisor() {
        return roleAccess.getSupervisorList().stream().map(x -> Role.toDTO(x)).collect(Collectors.toList());
    }

    @Override
    public List<Role> listAssistant() {
        return roleAccess.getAssistantList().stream().map(x -> Role.toDTO(x)).collect(Collectors.toList());
    }

}
