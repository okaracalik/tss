/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import jee18.dto.Role;

/**
 *
 * @author okaracalik
 */
public interface IRoleSystem {

    public List<Role> listSecretary();

    public List<Role> listEmployee();

    public List<Role> listSupervisor();

    public List<Role> listAssistant();

}
