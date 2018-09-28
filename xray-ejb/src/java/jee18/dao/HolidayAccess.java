/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.dao;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import jee18.entities.HolidayEntity;

/**
 *
 * @author esha
 */
@Stateless(name="HolidayAccess")
@LocalBean
public class HolidayAccess extends AbstractAccess implements IAccess<HolidayEntity> {

  
    public HolidayAccess() {
        super(HolidayEntity.class);
    }

    @Override
    public List<HolidayEntity> getList() {
        return em.createNamedQuery("HolidayEntity.getHolidayList", HolidayEntity.class).getResultList();
    }
    
   
    public List<HolidayEntity> getListByContractPeriod(Date startDate,Date endDate) {
    return em.createNamedQuery("HolidayEntity.getHolidayByPeriod", HolidayEntity.class)
            .setParameter("startDate",LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(startDate) ) )
            .setParameter("endDate",LocalDate.parse( new SimpleDateFormat("yyyy-MM-dd").format(endDate) ) )
            .getResultList();
    }
    @Override
    public HolidayEntity create(HolidayEntity a) {
          em.persist(a);
        return a;
    }

    @Override
    public HolidayEntity getByUuid(String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer updateByUuid(String uuid, HolidayEntity a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer deleteByUuid(String uuid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
