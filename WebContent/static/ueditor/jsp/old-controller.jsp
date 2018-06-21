<%@page import="com.alibaba.fastjson.JSONObject"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%

    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	
	String rootPath = application.getRealPath( "/" );
	//System.out.println(rootPath);
	String str=new ActionEnter( request, rootPath ).exec();
	JSONObject data=JSONObject.parseObject(str);
	String url=data.getString("url");
	if(url!=null){
		url=url.substring(url.indexOf("upload"), url.length());
		data.remove("url");
		data.put("url",url);
		str=data.toJSONString();
	}
	//System.out.println(str);
	out.write( str );
	
%>