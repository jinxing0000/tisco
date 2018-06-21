/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;
import com.tisco.modules.news.entity.NewsType;
import com.tisco.modules.news.dao.NewsTypeDao;

/**
 * 新闻类型Service
 * @author Ancle
 * @version 2016-10-05
 */
@Service
@Transactional(readOnly = true)
public class NewsTypeService extends CrudService<NewsTypeDao, NewsType> {

	@Autowired
	private NewsTypeDao newsTypeDao;
	public NewsType get(String id) {
		return super.get(id);
	}
	
	public List<NewsType> findList(NewsType newsType) {
		return super.findList(newsType);
	}
	/**
	 * 文章发布页面查询
	 * @param newsType
	 * @return
	 */
	public List<NewsType> findListForArticle(NewsType newsType){
		return newsTypeDao.findListForArticle(newsType);
	}
	
	public Page<NewsType> findPage(Page<NewsType> page, NewsType newsType) {
		return super.findPage(page, newsType);
	}
	
	@Transactional(readOnly = false)
	public void save(NewsType newsType) {
		super.save(newsType);
	}
	
	@Transactional(readOnly = false)
	public void delete(NewsType newsType) {
		super.delete(newsType);
	}
	/**
	 * app查询文章类型
	 * @return
	 */
	public List<NewsType> findNewsType(){
		return newsTypeDao.findNewsType();
	}
	
	
}