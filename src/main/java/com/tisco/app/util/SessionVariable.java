/**
 * @Company 航天科技山西公司
 * @Project jd-1.0v
 * @Package com.jd.util.api
 * @Title SessionVariable.java
 * @Description TODO(描述)
 * @author 张鸿
 * @create 2016年5月18日-上午11:41:41
 * @version V 1.0
 */
package com.tisco.app.util;

import java.io.Serializable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpRequest;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import com.weeln.common.security.shiro.session.SessionDAO;

/**
 * @Company 航天科技山西公司
 * @Project jd-1.0v
 * @Package com.jd.util.api
 * @ClassName SessionVariable.java
 * @Description TODO(描述)
 * @author 张鸿
 * @create 2016年5月18日-上午11:41:41
 */
public class SessionVariable {
	
	public static final String USER_ID = "api.userId";
	public static final String USER_NAME = "api.userName";
	/**
	 * 通过cookies 获取用户id
	 * @param request
	 * @return
	 */
	public static String  getSessionUserId(HttpServletRequest request,SessionDAO sessionDAO){
		String userId=null;
		Cookie[] cookies = request.getCookies();
		Serializable sessionId=null;
        for(Cookie c :cookies ){
        	if("weeln.session.id".equals(c.getName())){
        		sessionId=(Serializable)c.getValue();
        	}
            System.out.println(c.getName()+"--->"+c.getValue());
        }
        Session session = sessionDAO.readSession(sessionId);
        userId=(String)session.getAttribute(USER_ID);
        return userId;
	}

}
