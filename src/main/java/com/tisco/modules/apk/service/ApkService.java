/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.apk.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tisco.modules.apk.dao.ApkDao;
import com.tisco.modules.apk.entity.Apk;
import com.weeln.common.service.CrudService;

/**
 * apk Service
 * @author Ancle
 * @version 2016-10-05
 */
@Service
@Transactional(readOnly = true)
public class ApkService extends CrudService<ApkDao, Apk> {
	@Autowired
	private ApkDao apkDao;
	
	public Apk get(String id) {
		return super.get(id);
	}
	
	/**
	 * 查询最新的apk包
	 * @return
	 */
	public Apk findApk(){
		return apkDao.findApk();
	}
	
}