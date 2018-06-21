/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weeln.common.service.CrudService;
import com.tisco.modules.news.entity.NewsMessage;
import com.tisco.modules.news.dao.NewsMessageDao;

/**
 * 新闻类型Service
 * @author Ancle
 * @version 2016-10-05
 */
@Service
@Transactional(readOnly = true)
public class NewsMessageService extends CrudService<NewsMessageDao, NewsMessage> {

	/**
	 * 
	 */
	@Transactional(readOnly = false)
	public void save(NewsMessage newsMessage){
		super.save(newsMessage);
	}
	
	
	
}