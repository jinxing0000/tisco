/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.dao;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.tisco.modules.news.entity.NewsMessage;

/**
 * 新闻类型DAO接口
 * @author Ancle
 * @version 2016-10-05
 */
@MyBatisDao
public interface NewsMessageDao extends CrudDao<NewsMessage> {
	
}