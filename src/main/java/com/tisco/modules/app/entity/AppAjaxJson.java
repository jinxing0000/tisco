package com.tisco.modules.app.entity;

import com.weeln.common.json.AjaxJson;

public class AppAjaxJson extends AjaxJson {
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
