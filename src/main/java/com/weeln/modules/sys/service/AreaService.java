/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weeln.common.service.TreeService;
import com.weeln.modules.sys.dao.AreaDao;
import com.weeln.modules.sys.entity.Area;
import com.weeln.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @author weeln
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}
	
	@Transactional(readOnly = false)
	public List<Area> findListByType(String type) {
		return dao.findListByType(type);
	}
	
	@Transactional(readOnly = false)
	public List<Area> findSubList(String parentId) {
		return dao.findSubList(parentId);
	}

	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}
