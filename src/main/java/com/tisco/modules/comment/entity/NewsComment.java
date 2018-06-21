/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.comment.entity;

import org.hibernate.validator.constraints.Length;

import com.tisco.modules.news.entity.News;
import com.weeln.common.persistence.DataEntity;
import com.weeln.common.utils.excel.annotation.ExcelField;
import com.weeln.modules.sys.entity.User;

/**
 * 评论Entity
 * @author Ancle
 * @version 2016-10-06
 */
public class NewsComment extends DataEntity<NewsComment> {
	
	private static final long serialVersionUID = 1L;
	private String parentId;		// 父级ID
	private News news;
	private String comment;		// 评论内容
	private User commentator;		// 评论人ID
	private String isAnon;		// 是否匿名
	private String parentIds;		// 父级IDS
	private String nickName;   //昵称
	private String status;		//评论状态(1:已审核；0(其他)：待审核)
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public NewsComment() {
		super();
	}

	public NewsComment(String id){
		super(id);
	}

	@Length(min=0, max=64, message="父级ID长度必须介于 0 和 64 之间")
	@ExcelField(title="父级ID", align=2, sort=5)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public void setNews(News news) {
		this.news = news;
	}
	
	public News getNews() {
		return this.news;
	}
	
	@Length(min=0, max=500, message="评论内容长度必须介于 0 和 500 之间")
	@ExcelField(title="评论内容", align=2, sort=6)
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@ExcelField(title="评论人ID", align=2, sort=7)
	public User getCommentator() {
		return commentator;
	}

	public void setCommentator(User commentator) {
		this.commentator = commentator;
	}
	
	@Length(min=0, max=10, message="是否匿名长度必须介于 0 和 10 之间")
	@ExcelField(title="是否匿名", align=2, sort=8)
	public String getIsAnon() {
		return isAnon;
	}

	public void setIsAnon(String isAnon) {
		this.isAnon = isAnon;
	}
	
	@Length(min=0, max=1000, message="父级IDS长度必须介于 0 和 1000 之间")
	@ExcelField(title="父级IDS", align=2, sort=9)
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}