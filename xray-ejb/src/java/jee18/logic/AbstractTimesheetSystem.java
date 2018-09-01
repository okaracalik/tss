/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import jee18.dao.IAccess;
import jee18.entities.AbstractEntity;

/**
 *
 * @author okaracalik
 * @param <A>
 * @param <B>
 */
abstract public class AbstractTimesheetSystem<A, B extends AbstractEntity> implements ITimesheetSystem<A> {

    @EJB
    private IAccess<B> accessor;
    
    @Override
    public List<A> getList() {
        return accessor.getList().stream().map(x -> convertToObject(x)).collect(Collectors.toList());
    }

    @Override
    public A create(A a) {
        return convertToObject(accessor.create(convertToEntity(a)));
    }

    abstract protected B convertToEntity(A a);

    abstract protected A convertToObject(B b);

}
