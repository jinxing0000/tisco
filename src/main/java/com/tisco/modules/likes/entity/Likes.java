/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.likes.entity;

import org.hibernate.validator.constraints.Length;

import com.tisco.modules.news.entity.Article;
import com.weeln.common.persistence.DataEntity;
import com.weeln.common.utils.excel.annotation.ExcelField;
import com.weeln.modules.sys.entity.User;

/**
 * 点赞Entity
 * @author Ancle
 * @version 2016-10-05
 */
public class Likes extends DataEntity<Likes> {
	
	private static final long serialVersionUID = 1L;
	private User holder;		// 收藏人
	private String newsId;		// 新闻id
	private String newsUrl;		// 新闻URL
	
	private Article article;
	
	private String nickName;
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Likes() {
		super();
	}

	public Likes(String id){
		super(id);
	}

	@ExcelField(title="收藏人", align=2, sort=5)
	public User getHolder() {
		return holder;
	}

	public void setHolder(User holder) {
		this.holder = holder;
	}
	
	@Length(min=0, max=64, message="新闻id长度必须介于 0 和 64 之间")
	@ExcelField(title="新闻id", align=2, sort=6)
	public String getNewsId() {
		return newsId;
	}

	public void setNewsId(String newsId) {
		this.newsId = newsId;
	}
	
	@Length(min=0, max=64, message="新闻URL长度必须介于 0 和 64 之间")
	@ExcelField(title="新闻URL", align=2, sort=7)
	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
}