/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.echarts.dao;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.weeln.modules.echarts.entity.ChinaWeatherDataBean;

/**
 * 城市气温DAO接口
 * @author lgf
 * @version 2016-06-02
 */
@MyBatisDao
public interface ChinaWeatherDataBeanDao extends CrudDao<ChinaWeatherDataBean> {

	
}