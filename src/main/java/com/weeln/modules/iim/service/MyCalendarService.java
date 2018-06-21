/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.iim.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;
import com.weeln.modules.iim.dao.MyCalendarDao;
import com.weeln.modules.iim.entity.MyCalendar;


/**
 * 日历Service
 * @author liugf
 * @version 2016-04-19
 */
@Service
@Transactional(readOnly = true)
public class MyCalendarService extends CrudService<MyCalendarDao, MyCalendar> {

	public MyCalendar get(String id) {
		return super.get(id);
	}
	
	public List<MyCalendar> findList(MyCalendar myCalendar) {
		return super.findList(myCalendar);
	}
	
	public Page<MyCalendar> findPage(Page<MyCalendar> page, MyCalendar myCalendar) {
		return super.findPage(page, myCalendar);
	}
	
	@Transactional(readOnly = false)
	public void save(MyCalendar myCalendar) {
		super.save(myCalendar);
	}
	
	@Transactional(readOnly = false)
	public void delete(MyCalendar myCalendar) {
		super.delete(myCalendar);
	}
	
}