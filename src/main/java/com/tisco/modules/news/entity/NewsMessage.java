/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.entity;

import com.weeln.common.persistence.DataEntity;

/**
 * 新闻消息  Entity
 * @author Ancle
 * @version 2016-10-05
 */
public class NewsMessage extends DataEntity<NewsMessage> {
	private static final long serialVersionUID = 1L;
	private String newsId;		//新闻编号
	private String title;      //标题
	private String newsType;   //文章类型
	
	public NewsMessage() {
		super();
	}

	public NewsMessage(String id){
		super(id);
	}
	
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

}