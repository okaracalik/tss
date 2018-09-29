/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.logic.impl;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;
import jee18.dao.ContractAccess;
import jee18.dao.HolidayAccess;
import jee18.dto.Contract;
import jee18.dto.Holiday;
import jee18.entities.HolidayEntity;
import jee18.logic.AbstractTimesheetSystem;
import jee18.logic.IHolidaySystem;

/**
 *
 * @author esha
 */
@Stateless(name = "HolidaySystem")
public class HolidaySystem extends AbstractTimesheetSystem<Holiday, HolidayEntity> implements IHolidaySystem {

    @EJB
    private ContractAccess contractAccess;

    @EJB
    private HolidayAccess holidayAccess;

    public HolidaySystem() throws NamingException {
        super("HolidayAccess");
    }

    @Override
    protected HolidayEntity convertToEntity(Holiday p) {
        return Holiday.toEntity(p);
    }

    @Override
    protected Holiday convertToObject(HolidayEntity pe) {
        return Holiday.toDTO(pe);
    }

    @Override
    public List<Holiday> calculatePublicHolidaysInPeriod(String uuid) {
        Contract contract = Contract.toDTO(contractAccess.getByUuid(uuid));
        Date startDate = contract.getStartDate();
        Date endDate = contract.getEndDate();
        List<HolidayEntity> holidayEntitylist;
        holidayEntitylist = holidayAccess.getListByContractPeriod(startDate, endDate);
        List<Holiday> holidayList = super.convertEntityListToObjectList(holidayEntitylist);
        return holidayList;
    }

    @Override
    public List<Holiday> list() {
        return super.getList();
    }

    @Override
    public Holiday add(Holiday c) {
        return super.create(c);
    }

    @Override
    public Holiday get(String uuid) {
        return super.getByUuid(uuid);
    }

    @Override
    public Integer update(String uuid, Holiday c) {
        return super.updateByUuid(uuid, c);
    }

    @Override
    public Integer delete(String uuid) {
        return super.deleteByUuid(uuid);
    }

}
