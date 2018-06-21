package com.tisco.modules.news.entity;

import java.util.Date;

import com.weeln.common.persistence.DataEntity;
import com.weeln.modules.sys.entity.User;

public class AuditComment extends DataEntity<AuditComment> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 审核内容ID
	private String cmsId;
	// 审核人
	private User auditor;
	// 审核结论
	private String result;
	//　审核意见
	private String comments;
	// 审核时间
	private Date auditedTime;
	
	public AuditComment() {}
	
	public AuditComment(String cmsId) {
		this.cmsId = cmsId;
	}
	
	public String getCmsId() {
		return cmsId;
	}
	public void setCmsId(String cmsId) {
		this.cmsId = cmsId;
	}
	
	public User getAuditor() {
		return auditor;
	}
	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Date getAuditedTime() {
		return auditedTime;
	}
	public void setAuditedTime(Date auditedTime) {
		this.auditedTime = auditedTime;
	}
}
