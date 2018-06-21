/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.entity;

import org.hibernate.validator.constraints.Length;

import com.weeln.common.persistence.DataEntity;
import com.weeln.common.utils.excel.annotation.ExcelField;
import com.weeln.modules.sys.entity.User;

/**
 * 文章新闻Entity
 * @author Ancle
 * @version 2016-10-04
 */
public class News extends DataEntity<News> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private NewsType type;		// 类型
	private String summary;
	private String content;		// 新闻内容
	private String origin;		// 来源
	private User issuer;		// 发布人
	
	public News() {
		super();
	}

	public News(String id){
		super(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="类型长度必须介于 0 和 64 之间")
	@ExcelField(title="类型", dictType="", align=2, sort=8)
	public NewsType getType() {
		return type;
	}

	public void setType(NewsType type) {
		this.type = type;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	@ExcelField(title="新闻内容", align=2, sort=9)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="来源长度必须介于 0 和 64 之间")
	@ExcelField(title="来源", align=2, sort=10)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	public User getIssuer() {
		return issuer;
	}

	public void setIssuer(User issuer) {
		this.issuer = issuer;
	}
	
}