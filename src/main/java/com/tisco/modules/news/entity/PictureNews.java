/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.tisco.modules.fileupload.entity.FileUpload;
import com.weeln.common.persistence.DataEntity;
import com.weeln.common.utils.excel.annotation.ExcelField;
import com.weeln.modules.sys.entity.User;

/**
 * 图片新闻Entity
 * @author Ancle
 * @version 2016-10-12
 */
public class PictureNews extends DataEntity<PictureNews> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String summary;		// 摘要
	private String content;		// 内容
	private String origin;		// 来源
	private User issuer;		// 发布人
	
	private List<FileUpload> fileUploads;//附件
	
	public PictureNews() {
		super();
	}

	public PictureNews(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标题长度必须介于 0 和 200 之间")
	@ExcelField(title="标题", align=2, sort=4)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=500, message="摘要长度必须介于 0 和 500 之间")
	@ExcelField(title="摘要", align=2, sort=5)
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@ExcelField(title="内容", align=2, sort=6)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=64, message="来源长度必须介于 0 和 64 之间")
	@ExcelField(title="来源", align=2, sort=7)
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
	@ExcelField(title="发布人", align=2, sort=8)
	public User getIssuer() {
		return issuer;
	}

	public void setIssuer(User issuer) {
		this.issuer = issuer;
	}

	public List<FileUpload> getFileUploads() {
		return fileUploads;
	}

	public void setFileUploads(List<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}
}