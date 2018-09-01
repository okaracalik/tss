/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;

/**
 *
 * @author okaracalik
 */
public interface ITimesheetSystem<A> {
    
    public List<A> getList();
    
    public A create(A a);
    
}
