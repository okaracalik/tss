/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;


import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import logic.PersonLogic;

/**
 *
 * @author apple
 */
@Named
@RequestScoped
public class TestDataGeneratorBean {
    @EJB
    private PersonLogic personLogic;

    public void createTestData() {
        personLogic.createTestData();
    }
}
