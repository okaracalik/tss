/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import javax.ejb.Stateless;
import jee18.logic.IAppSystem;

/**
 *
 * @author okaracalik
 */
@Stateless(name = "AppSystem")
public class AppSystem implements IAppSystem {

    @Override
    public void generateData() {
        // get persons
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void truncateData() {
        // remove roles
        // remove persons
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
