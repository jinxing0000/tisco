/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.dao;

import java.util.List;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.tisco.modules.news.entity.NewsType;

/**
 * 新闻类型DAO接口
 * @author Ancle
 * @version 2016-10-05
 */
@MyBatisDao
public interface NewsTypeDao extends CrudDao<NewsType> {
    /**
     * 查询发布文章是
     * @param newsType
     * @return
     */
	public List<NewsType> findListForArticle(NewsType newsType);
	/**
	 * app查询新闻类型
	 * @return
	 */
	public List<NewsType> findNewsType();
	
}