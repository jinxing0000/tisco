/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.iim.dao;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.weeln.modules.iim.entity.MyCalendar;


/**
 * 日历DAO接口
 * @author liugf
 * @version 2016-04-19
 */
@MyBatisDao
public interface MyCalendarDao extends CrudDao<MyCalendar> {
	
}