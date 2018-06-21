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
//			String ueditPath = pro.getProperty("ueditPath");//富文本编辑器文件保存路径
//			servletContext.setAttribute("ueditPath", ueditPath);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}//获取配置文件的对象
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		return super.initWebApplicationContext(servletContext);
	}
}
