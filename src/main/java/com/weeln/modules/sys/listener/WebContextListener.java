package com.weeln.modules.sys.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;

import com.weeln.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
//		Properties pro=new Properties();
//		try {
//			pro.load(WebContextListener.class.getResourceAsStream("/appversion.properties"));
//			String ueditPath = pro.getProperty("ueditPath");//���ı��༭���ļ�����·��
//			servletContext.setAttribute("ueditPath", ueditPath);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//��ȡ�����ļ��Ķ���
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
}
