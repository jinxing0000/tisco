/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.common.json;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.weeln.common.mapper.JsonMapper;


/**
 * $.ajax后需要接受的JSON
 * 
 * @author
 * 
 */
public class AjaxJson {
    
	public static final String OK = "0";
	public static final String ParaLarge = "1";
	public static final String ParaIllegal = "2";
	public static final String PayTypeError = "3";
	public static final String ParaNull = "4";
	public static final String Invalid = "5";
	public static final String NotExist = "6";
	public static final String Error = "7";
	public static final String UpateError = "8";
	public static final String UplError = "9";
	public static final String Exist = "10";
	public static final String SaveError = "13";
	public static final String AppUpdateError = "14";
	
	
	public final static Map<String,String> ErrorMsgMap = new HashMap<String,String>();  
	static {  
		ErrorMsgMap.put("0", "ok");  
		ErrorMsgMap.put("1", " is too large.");  
		ErrorMsgMap.put("2", " is illegal.");  
		ErrorMsgMap.put("3", " Invalid.");  
		ErrorMsgMap.put("4", " is null.");
		ErrorMsgMap.put("5", " token Invalid.");
		ErrorMsgMap.put("6", " does not exist. ");
		ErrorMsgMap.put("7", " error. ");
		ErrorMsgMap.put("8", " update error. ");
		ErrorMsgMap.put("9", " upload error. ");
		ErrorMsgMap.put("10", " exist. ");
		ErrorMsgMap.put("13", " save error. ");
		ErrorMsgMap.put("14", "app  update error. ");
	}  
	
	public final static Map<Integer, String> ErrorCodeMap = new HashMap<Integer, String>();
	static {  
		ErrorCodeMap.put(0, "ok");  
		ErrorCodeMap.put(1, "参数错误");  
		ErrorCodeMap.put(2, "用户不存在");  
		ErrorCodeMap.put(3, "密码错误");  
		ErrorCodeMap.put(4, "修改密码失败");
		ErrorCodeMap.put(5, "登陆失效");
		ErrorCodeMap.put(6, "上传图片流不存在");
		ErrorCodeMap.put(7, "揽件信息保存失败！");
		ErrorCodeMap.put(8, "版本更新失败！");
		ErrorCodeMap.put(9, "用户注销失败！");
		ErrorCodeMap.put(10, "快递单号已存在！");
		ErrorCodeMap.put(11, "快递单号不存在！");
		ErrorCodeMap.put(12, "提件信息保存失败！");
		ErrorCodeMap.put(13, "设置用户手机编号失败！");
		ErrorCodeMap.put( 14, "app版本查询失败！");
	}
	
	
	private boolean success = true;// 是否成功
	private String errorCode = "-1";//错误代码
	private String msg = "操作成功";// 提示信息
	private LinkedHashMap<String, Object> body = new LinkedHashMap();//封装json的map
	
	public LinkedHashMap<String, Object> getBody() {
		return body;
	}

	public void setBody(LinkedHashMap<String, Object> body) {
		this.body = body;
	}

	public void put(String key, Object value){//向json中添加属性，在js中访问，请调用data.map.key
		body.put(key, value);
	}
	
	public void remove(String key){
		body.remove(key);
	}
	
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {//向json中添加属性，在js中访问，请调用data.msg
		this.msg = msg;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	@JsonIgnore//返回对象时忽略此属性
	public String getJsonStr() {//返回json字符串数组，将访问msg和key的方式统一化，都使用data.key的方式直接访问。

		String json = JsonMapper.getInstance().toJson(this);
		return json;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}
	
	public static String error(String errorcode){
		return ErrorMsgMap.get(errorcode);
	}
	
}
