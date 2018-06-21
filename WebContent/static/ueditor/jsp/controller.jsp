<%@page import="com.tisco.app.util.UeditorUploadFileUtil"%>
<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	String action=request.getParameter("action");
	if("config".equals(action)){
		String str=new ActionEnter( request, rootPath ).exec();
		out.write( str);
	}else{
		String str=UeditorUploadFileUtil.uploadFile(request, response, rootPath);
		out.write(str);
	}
%>