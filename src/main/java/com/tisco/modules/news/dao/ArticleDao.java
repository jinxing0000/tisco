/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tisco.modules.news.entity.Article;
import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;

/**
 * 文章新闻DAO接口
 * @author Ancle
 * @version 2016-10-04
 */
@MyBatisDao
public interface ArticleDao extends CrudDao<Article> {

	/**
	 * 获取新闻的阅读量
	 * @param newsId
	 * @return
	 */
	public Integer getReadingAmount(@Param("newsId")String newsId);
	
	public void insertOne(@Param("newsId")String newsId, @Param("amount")int amount);
	
	public void updateReadingAmount(@Param("newsId")String newsId);
	
	public void deleteReadingAmount(@Param("newsId")String newsId);
	/**
	 * 查询我收藏的新闻
	 * @param article
	 * @return
	 */
	public List<Article> findListByFavorite(Article article);
	
	public void updateArticleStatus(Article article);
	
	public void updateArticleContentType(Article article);
	/**
	 * 查询推荐新闻
	 * @param map
	 * @return
	 */
	public List<Article> recommendNews(Map<String, Object> map);
	
	/**
	 * 新版新闻列表
	 * @param article
	 * @return
	 */
	public List<Article> appTiscoFindPage(Article article);
	
	public List<Article> appTiscoFocusList(Article article);
}