/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.weeln.common.persistence.TreeDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.weeln.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @author weeln
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	public List<Area> findListByType(String type);
	
	public List<Area> findSubList(@Param("parentId") String parentId);
}
