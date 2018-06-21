/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.entity;

import org.hibernate.validator.constraints.Length;

import com.weeln.common.persistence.DataEntity;
import com.weeln.common.utils.excel.annotation.ExcelField;

/**
 * 新闻类型Entity
 * @author Ancle
 * @version 2016-10-05
 */
public class NewsType extends DataEntity<NewsType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 类型名称
	private int	sort;
	private String key;
	private String shortName;//简称
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public NewsType() {
		super();
	}

	public NewsType(String id){
		super(id);
	}

	@Length(min=0, max=64, message="类型名称长度必须介于 0 和 64 之间")
	@ExcelField(title="类型名称", align=2, sort=4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}
	
	
}