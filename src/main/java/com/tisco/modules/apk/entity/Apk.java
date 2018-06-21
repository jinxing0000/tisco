/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.apk.entity;

import java.util.Date;

import com.weeln.common.persistence.DataEntity;

/**
 * apk Entity
 * @author Ancle
 * @version 2016-10-05
 */
public class Apk extends DataEntity<Apk> {
	
	private static final long serialVersionUID = 1L;
	
	private String apkName;//apk名称
	
	private String apkVersion;//apk版本
	
	private String apkPath;//apk路径
	
	private String apkMessage;//apk提示语
	
	private Date uploadTime;//上传时间
	
	private String uploadUserId;//上传人
	
	private String apkVersionCode;//版本code

	public Apk() {
		super();
	}

	public Apk(String id){
		super(id);
	}
	
	public String getApkName() {
		return apkName;
	}

	public void setApkName(String apkName) {
		this.apkName = apkName;
	}

	public String getApkVersion() {
		return apkVersion;
	}

	public void setApkVersion(String apkVersion) {
		this.apkVersion = apkVersion;
	}

	public String getApkPath() {
		return apkPath;
	}

	public void setApkPath(String apkPath) {
		this.apkPath = apkPath;
	}

	public String getApkMessage() {
		return apkMessage;
	}

	public void setApkMessage(String apkMessage) {
		this.apkMessage = apkMessage;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}
	
	public String getApkVersionCode() {
		return apkVersionCode;
	}

	public void setApkVersionCode(String apkVersionCode) {
		this.apkVersionCode = apkVersionCode;
	}
}