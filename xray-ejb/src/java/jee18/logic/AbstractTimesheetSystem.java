/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import java.util.stream.Collectors;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import jee18.dao.IAccess;
import jee18.entities.AbstractEntity;

/**
 *
 * @author okaracalik
 * @param <A>
 * @param <B>
 */
abstract public class AbstractTimesheetSystem<A, B extends AbstractEntity> implements ITimesheetManagementSystem<A> {

    private IAccess<B> accessor;

    public AbstractTimesheetSystem(String name) throws NamingException {
        InitialContext ctx = new InitialContext();
        Object fObj = ctx.lookup("java:global/xray/xray-ejb/" + name + "!jee18.dao.IAccess");
        accessor = (IAccess<B>) fObj;
    }

    @Override
    public List<A> getList() {
        return accessor.getList().stream().map(x -> convertToObject(x)).collect(Collectors.toList());
    }

    @Override
    public A create(A a) {
        return convertToObject(accessor.create(convertToEntity(a)));
    }

    @Override
    public A getByUuid(String uuid) {
        B entity = accessor.getByUuid(uuid);
        if (entity == null) {
            return null;
        }
        return convertToObject(entity);
    }

    @Override
    public Integer updateByUuid(String uuid, A a) {
        System.out.print(uuid + ": "+ a.toString());
        return accessor.updateByUuid(uuid, convertToEntity(a));
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        return accessor.deleteByUuid(uuid);
    }

    abstract protected B convertToEntity(A a);

    abstract protected A convertToObject(B b);

}
