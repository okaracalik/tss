/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.util.List;
import jee18.entities.AbstractEntity;

/**
 *
 * @author okaracalik
 * @param <A>
 */
public interface IAccess<A extends AbstractEntity> {

    public List<A> getList();

    public A create(A a);

    public A getByUuid(String uuid);

    public Integer updateByUuid(String uuid, A a);

    public Integer deleteByUuid(String uuid);

}
