package com.tisco.modules.fileupload.entity;

import com.weeln.common.persistence.DataEntity;

public class FileUpload extends DataEntity<FileUpload> {
	private static final long serialVersionUID = 1L;
	private String name;// 文件名字
	private String type;// 文件类型
	private String path;// 文件上传路径
	private String policyinfoId;// 政策法规id
	private String tag;// 标识位
	private int sort;//排序
	private long whenLong;

	public long getWhenLong() {
		return whenLong;
	}

	public void setWhenLong(long whenLong) {
		this.whenLong = whenLong;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPolicyinfoId() {
		return policyinfoId;
	}

	public void setPolicyinfoId(String policyinfoId) {
		this.policyinfoId = policyinfoId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

 

}
