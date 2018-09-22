/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic;

import java.util.List;
import jee18.dto.Holiday;
import jee18.dto.Holiday;

/**
 *
 * @author esha
 */
public interface IHolidaySystem {
     public List<Holiday> list();

    public Holiday add(Holiday c);


    public Holiday get(String uuid);

    // RULE: if prepared
    public Integer update(String uuid, Holiday c);

    
    public Integer delete(String uuid);

    
    public void print();
      
    public List<Holiday> calculatePublicHolidaysInPeriod(String uuid) ; 
}
