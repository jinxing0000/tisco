package com.tisco.app.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Company 航天科技山西公司
 * @Project jd-1.0v
 * @Package com.jd.util.api
 * @ClassName ErrorInfo.java
 * @Description TODO(描述)
 * @author 张鸿
 * @create 2016年5月26日-下午1:56:21
 */
public class ErrorInfo {
	
	public static final String OK = "0";
	public static final String ParaLarge = "1";
	public static final String ParaIllegal = "2";
	public static final String PayTypeError = "3";
	public static final String ParaNull = "4";
	public static final String Invalid = "5";
	public static final String VerifycodeError = "6";
	public static final String RegisterFailed = "7";
	public static final String VerifycodeInvalid = "8";
	public static final String PhonenumRegistered = "9";
	public static final String LogonFailed= "10";
	public static final String UserNamePasswordError= "11";
	public static final String UserLocking= "12";
	public static final String UserNone= "13";
	public static final String ExitFailure= "14";
	public static final String SelectNewsTypeError= "15";
	public static final String SelectNewsError= "16";
	public static final String SelectArticleInfoError= "17";
	public static final String SaveCommentError= "18";
	public static final String ArticlesHaveBeenCollected= "19";
	public static final String SaveFavoriteError= "20";
	public static final String NotCollected= "21";
	public static final String DelFavoriteError= "22";
	public static final String SelectCommentError= "23";
	public static final String SelectFavoriteNewsError= "24";
	public static final String SaveLikesError= "25";
	public static final String DelLikesError= "26";
	public static final String NoLikes= "27";
	public static final String ApkError= "28";
	public static final String SMSSendFailure= "29";
	public static final String SMSVerifycodeError = "30";
	public static final String ModifyPasswordError = "31";
	public static final String OldPasswordError = "32";
	public static final String ResetPasswordError = "33";
	public static final String USER_DOES_NOT_EXIST = "34";
	
	
	public final static Map<String,String> ErrorMsgMap = new HashMap<String,String>();  
	static {  
		ErrorMsgMap.put("0", "ok");  
		ErrorMsgMap.put("1", " is too large.");  
		ErrorMsgMap.put("2", " is illegal.");  
		ErrorMsgMap.put("3", " Invalid.");  
		ErrorMsgMap.put("4", " is null.");
		ErrorMsgMap.put("5", " token Invalid.");
		ErrorMsgMap.put("6", " Failed to obtain verification code.");
		ErrorMsgMap.put("7", " register failed.");
		ErrorMsgMap.put("8", " verifycode invalid.");
		ErrorMsgMap.put("9", " phonenum registered.");
		ErrorMsgMap.put("10", "logon failed.");
		ErrorMsgMap.put("11", "username password error.");
		ErrorMsgMap.put("12", "user locking.");
		ErrorMsgMap.put("13", "user does not exist.");
		ErrorMsgMap.put("14", "exit failure.");
		ErrorMsgMap.put("15", "select newstype error.");
		ErrorMsgMap.put("16", "select news error.");
		ErrorMsgMap.put("17", "select article info error.");
		ErrorMsgMap.put("18", "save comment error.");
		ErrorMsgMap.put("19", "articles have been collected.");
		ErrorMsgMap.put("20", "save favorite error.");
		ErrorMsgMap.put("21", "not collected.");
		ErrorMsgMap.put("22", "del favorite error.");
		ErrorMsgMap.put("23", "select comment error.");
		ErrorMsgMap.put("24", "select favorite newsError.");
		ErrorMsgMap.put("25", "save likes error.");
		ErrorMsgMap.put("26", "delete likes error.");
		ErrorMsgMap.put("27", "no likes.");
		ErrorMsgMap.put("28", "apk version error.");
		ErrorMsgMap.put("29", "SMS send failure.");
		ErrorMsgMap.put("30", "SMS Verifycode Error.");
		ErrorMsgMap.put("31", "Modify password Error.");
		ErrorMsgMap.put("32", "old password Error.");
		ErrorMsgMap.put("33", "reset password Error.");
		ErrorMsgMap.put("34", "user does not exist.");
	}  
	
	public final static Map<Integer, String> ErrorCodeMap = new HashMap<Integer, String>();
	static {  
		ErrorCodeMap.put(0, "ok");  
		ErrorCodeMap.put(1, "参数错误");  
		ErrorCodeMap.put(2, "用户不存在");  
		ErrorCodeMap.put(3, "密码错误");  
		ErrorCodeMap.put(4, "修改密码失败");
		ErrorCodeMap.put(5, "登陆失效");
		ErrorCodeMap.put(6, "获取验证码失败");
		ErrorCodeMap.put(7, "注册失败");
		ErrorCodeMap.put(8, "验证码失效");
		ErrorCodeMap.put(9, "手机号已注册");
		ErrorCodeMap.put(10, "登陆失败");
		ErrorCodeMap.put(11, "用户名/密码错误");
		ErrorCodeMap.put(12, "用户锁定");
		ErrorCodeMap.put(13, "用户不存在");
		ErrorCodeMap.put(14, "用户退出失败");
		ErrorCodeMap.put(15, "查询新闻类型失败");
		ErrorCodeMap.put(16, "查询新闻失败");
		ErrorCodeMap.put(17, "查询新闻详情失败");
		ErrorCodeMap.put(18, "保存评论信息失败");
		ErrorCodeMap.put(19, "新闻已收藏");
		ErrorCodeMap.put(20, "新闻收藏失败");
		ErrorCodeMap.put(21, "新闻未收藏");
		ErrorCodeMap.put(22, "新闻取消收藏失败");
		ErrorCodeMap.put(24, "查询收藏列表失败");
		ErrorCodeMap.put(25, "保存点赞失败");
		ErrorCodeMap.put(26, "取消点赞失败");
		ErrorCodeMap.put(27, "文章未点赞");
		ErrorCodeMap.put(28, "apk版本查询失败");
		ErrorCodeMap.put(29, "短信发送失败");
		ErrorCodeMap.put(30, "获取短信验证码失败");
		ErrorCodeMap.put(31, "修改密码失败");
		ErrorCodeMap.put(32, "原密码错误");
		ErrorCodeMap.put(33, "密码重置错误");
		ErrorCodeMap.put(34, "用户不存在");
	}  
	/**
	 * 
	 * @Title error
	 * @Description (描述)
	 * @author 张鸿
	 * @create 2016年5月18日-下午2:23:55
	 * @Param @param name
	 * @Param @param errorcode
	 * @Param @return
	 * @Param @throws JsonGenerationException
	 * @Param @throws JsonMappingException
	 * @Param @throws IOException
	 * @return String
	 * @throws
	 */
	public static JSONObject error(int errorcode,String name,String msgcode){
		ResultPojo resultPojo = new ResultPojo();
		resultPojo.setErrcode(errorcode);
		resultPojo.setErrmsg(name+ErrorInfo.ErrorMsgMap.get(msgcode));
		return (JSONObject) JSON.toJSON(resultPojo);
	}
	/**
	 * 
	 * @Title success
	 * @Description (描述)
	 * @author 张鸿
	 * @create 2016年5月18日-下午2:23:46
	 * @Param @param data
	 * @Param @return
	 * @Param @throws JsonGenerationException
	 * @Param @throws JsonMappingException
	 * @Param @throws IOException
	 * @return String
	 * @throws
	 */
	public static  JSONObject  success (JSONObject data) throws JsonGenerationException, JsonMappingException, IOException{
		ResultPojo resultPojo = new ResultPojo();
		resultPojo.setErrcode(0);
		resultPojo.setErrmsg(ErrorMsgMap.get(OK));
		resultPojo.setData(data);
		return (JSONObject) JSON.toJSON(resultPojo);
	}
}
