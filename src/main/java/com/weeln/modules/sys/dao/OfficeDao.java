/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.sys.dao;

import com.weeln.common.persistence.TreeDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.weeln.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @author weeln
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	public Office getByCode(String code);
}
