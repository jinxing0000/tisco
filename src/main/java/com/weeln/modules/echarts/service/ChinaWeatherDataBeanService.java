/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.echarts.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;
import com.weeln.modules.echarts.entity.ChinaWeatherDataBean;
import com.weeln.modules.echarts.dao.ChinaWeatherDataBeanDao;

/**
 * 城市气温Service
 * @author lgf
 * @version 2016-06-02
 */
@Service
@Transactional(readOnly = true)
public class ChinaWeatherDataBeanService extends CrudService<ChinaWeatherDataBeanDao, ChinaWeatherDataBean> {

	public ChinaWeatherDataBean get(String id) {
		return super.get(id);
	}
	
	public List<ChinaWeatherDataBean> findList(ChinaWeatherDataBean chinaWeatherDataBean) {
		return super.findList(chinaWeatherDataBean);
	}
	
	public Page<ChinaWeatherDataBean> findPage(Page<ChinaWeatherDataBean> page, ChinaWeatherDataBean chinaWeatherDataBean) {
		return super.findPage(page, chinaWeatherDataBean);
	}
	
	@Transactional(readOnly = false)
	public void save(ChinaWeatherDataBean chinaWeatherDataBean) {
		super.save(chinaWeatherDataBean);
	}
	
	@Transactional(readOnly = false)
	public void delete(ChinaWeatherDataBean chinaWeatherDataBean) {
		super.delete(chinaWeatherDataBean);
	}
	
	
	
	
}