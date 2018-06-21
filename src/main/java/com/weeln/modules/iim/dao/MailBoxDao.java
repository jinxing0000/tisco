/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.iim.dao;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.weeln.modules.iim.entity.MailBox;

/**
 * 发件箱DAO接口
 * @author weeln
 * @version 2015-11-15
 */
@MyBatisDao
public interface MailBoxDao extends CrudDao<MailBox> {
	
	public int getCount(MailBox entity);
	
}