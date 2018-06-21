/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.apk.dao;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.tisco.modules.apk.entity.Apk;

/**
 * apk DAO接口
 * @author Ancle
 * @version 2016-10-05
 */
@MyBatisDao
public interface ApkDao extends CrudDao<Apk> {
    /**
     * 查询最新的apk信息
     * @return
     */
	public Apk findApk();
}