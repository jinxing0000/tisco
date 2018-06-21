package com.tisco.modules.app.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.collect.Lists;
import com.tisco.app.util.ErrorInfo;
import com.tisco.app.util.IDCMAS;
import com.tisco.app.util.SessionVariable;
import com.weeln.common.config.Global;
import com.weeln.common.utils.StringUtils;
import com.weeln.common.web.BaseController;
import com.weeln.modules.sys.entity.Role;
import com.weeln.modules.sys.entity.User;
import com.weeln.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.weeln.modules.sys.security.UsernamePasswordToken;
import com.weeln.modules.sys.service.SystemService;
import com.weeln.modules.sys.utils.UserUtils;

import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "${adminPath}/app/user")
public class AppUserController extends BaseController {
	@Autowired
	private SystemService systemService;
	
	/**
	 * 获取手机验证码接口
	 * @param request
	 * @param mapData
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="verifycode",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject verifycode(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp){
		JSONObject data=new JSONObject();
		try {
			// 生成随机的6位数验证码
			String randomCode = String.valueOf((int) (Math.random() * 900000 + 100000));
//			SystemConfig config = systemConfigService.get("1");
//			//发送验证码短息
//			String sendResult = UserUtils.sendRandomCode(config.getSmsName(),config.getSmsPassword(), phoneNum, randomCode);
			Map<String, String> codeInfo = new HashMap<String, String>();
			codeInfo.put("code", randomCode);
			String sendTime=String.valueOf(System.currentTimeMillis());
			codeInfo.put("sendTime", sendTime);
			request.getSession().setAttribute("randomCode", codeInfo);
			request.getSession().setMaxInactiveInterval(60);
			Cookie[] cookies = request.getCookies();
			int count=0;
			for(Cookie c :cookies ){
				System.out.println(c.getName()+"----------------------");
	        	if("JSESSIONID".equals(c.getName())||"weeln.session.id".equals(c.getName())){
	        		c.setMaxAge(60);
	        		resp.addCookie(c);
	        		count++;
	        		//System.out.println("执行放重新设置cookie++++++++++++++++++++++++++++++++++++++++++++");
	        	}
	        }
			if(count==0){
				Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId().toString());
				cookie.setMaxAge(60);
				resp.addCookie(cookie);
				//System.out.println("执行放cookie++++++++++++++++++++++++++++++++++++++++++++");
			}
//			String phone[]={"18335461816"};
//			String params[]={randomCode};
//			String tempID="4885";
//			boolean flag=IDCMAS.sendTSMS(phone, params, tempID);
//			if(!flag){
//				return ErrorInfo.error(29,"", ErrorInfo.SMSSendFailure);
//			}
			data.put("verifycode", randomCode);
			data.put("sendTime", sendTime);
			//System.out.println(randomCode+"图形验证码++++++++++++++++++++++++++++");
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(6,"", ErrorInfo.VerifycodeError);
		}
	}
	/**
	 * 发送短信验证码
	 * @param request
	 * @param mapData
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="SMSverifycode",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject SMSverifycode(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp){
		JSONObject data=new JSONObject();
		try {
			String phoneNum=mapData.get("phoneNum");
			String verifycode=mapData.get("verifycode");
			String type=mapData.get("type");
			//判断手机号是否存在
			if(StringUtils.isEmpty(phoneNum)){
				return ErrorInfo.error(1,"phoneNum", ErrorInfo.ParaNull);
			}
			if(StringUtils.isEmpty(phoneNum)){
				return ErrorInfo.error(1,"verifycode", ErrorInfo.ParaNull);
			}
			if(StringUtils.isEmpty(type)){
				return ErrorInfo.error(1,"type", ErrorInfo.ParaNull);
			}
			Map<String, String> codeInfo = (Map<String, String>)request.getSession().getAttribute("randomCode");
			boolean result = false;
			System.out.println(codeInfo);
			if (codeInfo != null) {
				// 获取来自请求的验证码
				String reqCode = verifycode;
				// 获取当前系统时间的毫秒数
				long curTime = System.currentTimeMillis();
				// 获取保存在session的验证码
				String savedCode = codeInfo.get("code");
				// 获取验证码发送时间的毫秒数
				long sendTime = Long.parseLong(codeInfo.get("sendTime"));
				if (reqCode.equals(savedCode)) {
					long timeDiff = curTime - sendTime;
					if (timeDiff < Global.CODE_VALID_TIME) {
						result = true;
					}
				}
			}
			if(!result){
				return ErrorInfo.error(8,"", ErrorInfo.VerifycodeInvalid);
			}
			// 生成随机的6位数验证码
			String randomCode = String.valueOf((int) (Math.random() * 900000 + 100000));
//			SystemConfig config = systemConfigService.get("1");
//			//发送验证码短息
//			String sendResult = UserUtils.sendRandomCode(config.getSmsName(),config.getSmsPassword(), phoneNum, randomCode);
			Map<String, String> SAMcodeInfo = new HashMap<String, String>();
			SAMcodeInfo.put("code", randomCode);
			String sendTime=String.valueOf(System.currentTimeMillis());
			SAMcodeInfo.put("sendTime", sendTime);
			request.getSession().setAttribute("SMS_"+phoneNum, SAMcodeInfo);
			request.getSession().setMaxInactiveInterval(60);
			Cookie[] cookies = request.getCookies();
			int count=0;
			for(Cookie c :cookies ){
				System.out.println(c.getName()+"----------------------");
	        	if("JSESSIONID".equals(c.getName())||"weeln.session.id".equals(c.getName())){
	        		c.setMaxAge(60);
	        		resp.addCookie(c);
	        		count++;
	        		//System.out.println("执行放重新设置cookie++++++++++++++++++++++++++++++++++++++++++++");
	        	}
	        }
			if(count==0){
				Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId().toString());
				cookie.setMaxAge(60);
				resp.addCookie(cookie);
				//System.out.println("执行放cookie++++++++++++++++++++++++++++++++++++++++++++");
			}
			data.put("phoneNum", phoneNum);
			//data.put("SMSverifycode", randomCode);
			data.put("sendTime", sendTime);
//			System.out.println(randomCode+"SMS验证码++++++++++++++++++++++++++++");
			String phone[]={phoneNum};
			String params[]={randomCode};
			String tempID="";
			//注册短信验证码
			if("register".equals(type)){
				tempID="4701";
			}
			//修改密码短信验证码
			else if("modifyPassword".equals(type)){
				tempID="4885";
			}
			//重置密码短信
			else if("resetPassword".equals(type)){
				tempID="8017";
			}
			boolean flag=IDCMAS.sendTSMS(phone, params, tempID);
			if(!flag){
				return ErrorInfo.error(29,"", ErrorInfo.SMSSendFailure);
			}
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(30,"", ErrorInfo.SMSVerifycodeError);
		}
	}
	
	
	
	/**
	 * 用户注册
	 * @param request
	 * @param mapData
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="register",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject register(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp){
		JSONObject data=new JSONObject();
		try {
			String phoneNum=mapData.get("phoneNum");
			//判断手机号是否存在
			if(StringUtils.isEmpty(phoneNum)){
				return ErrorInfo.error(1,"phoneNum", ErrorInfo.ParaNull);
			}
			String verifycode=mapData.get("SMSverifycode");
			//判断验证码是否存在
			if(StringUtils.isEmpty(verifycode)){
				return ErrorInfo.error(1,"verifycode", ErrorInfo.ParaNull);
			}
			//判断昵称是否存在
			String nickName=mapData.get("nickName");
			//判断手机号是否存在
			if(StringUtils.isEmpty(nickName)){
				return ErrorInfo.error(1,"verifycode", ErrorInfo.ParaNull);
			}
			//判断密码是否存在
			String password=mapData.get("password");
			//判断手机号是否存在
			if(StringUtils.isEmpty(password)){
				return ErrorInfo.error(1,"password", ErrorInfo.ParaNull);
			}
			Map<String, String> codeInfo = (Map<String, String>)request.getSession().getAttribute("SMS_"+phoneNum);
			boolean result = false;
			System.out.println(codeInfo);
			if (codeInfo != null) {
				// 获取来自请求的验证码
				String reqCode = verifycode;
				// 获取当前系统时间的毫秒数
				long curTime = System.currentTimeMillis();
				// 获取保存在session的验证码
				String savedCode = codeInfo.get("code");
				// 获取验证码发送时间的毫秒数
				long sendTime = Long.parseLong(codeInfo.get("sendTime"));
				if (reqCode.equals(savedCode)) {
					long timeDiff = curTime - sendTime;
					if (timeDiff < Global.CODE_VALID_TIME) {
						result = true;
					}
				}
			}
			if(!result){
				return ErrorInfo.error(8,"", ErrorInfo.VerifycodeInvalid);
			}
			//判断此手机号是否已经注册
			if(systemService.getUserByLoginName(phoneNum) != null){
				return ErrorInfo.error(9,"", ErrorInfo.PhonenumRegistered);
			}
			User user=new User();
			user.setLoginName(phoneNum);
			user.setPassword(SystemService.entryptPassword(password));
			// 设置注册方式/途径为app
			user.setRegWay(User.USER_REG_WAY_APP);
			user.setMobile(user.getLoginName());
			user.setName(nickName);
			// 设置App会员默认角色
			Role role = systemService.getRoleByEnname(Global.APP_USER_ROLE_NAME_EN);
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(role);
			user.setRoleList(roleList);
			systemService.regSave(user);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(7,"", ErrorInfo.RegisterFailed);
		}
	}
	
	/**
	 * 登陆接口
	 * @param request
	 * @param mapData
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="login",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject login(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp){
		JSONObject data=new JSONObject();
		try {
			String userName=mapData.get("userName");
			//判断用户名是否存在
			if(StringUtils.isEmpty(userName)){
				return ErrorInfo.error(1,"userName", ErrorInfo.ParaNull);
			}
			String password=mapData.get("password");
			//判断密码是否存在
			if(StringUtils.isEmpty(password)){
				return ErrorInfo.error(1,"password", ErrorInfo.ParaNull);
			}
			// 获取登录主机地址
			String host = StringUtils.getRemoteAddr(request);
			// 图形验证码
			String captcha = null;
			// 手机登录标识
			boolean mobile = true;
			UsernamePasswordToken token = new UsernamePasswordToken(userName, password.toCharArray(), true, host, captcha, mobile);
			Subject userObj = SecurityUtils.getSubject();
			userObj.login(token);
			if (userObj.isAuthenticated()) {
				Principal principal = (Principal)userObj.getPrincipal();
				if (principal != null) {
					Cookie[] cookies = request.getCookies();
					request.getSession().setAttribute(SessionVariable.USER_ID, principal.getId());
					request.getSession().setAttribute(SessionVariable.USER_NAME, principal.getName());
					request.getSession().setMaxInactiveInterval(30*24*60*60);
					int count=0;
					for(Cookie c :cookies ){
						System.out.println(c.getName()+"----------------------");
			        	if("JSESSIONID".equals(c.getName())||"weeln.session.id".equals(c.getName())){
			        		c.setMaxAge(30*24*60*60);
			        		resp.addCookie(c);
			        		count++;
			        		//System.out.println("执行放重新设置cookie++++++++++++++++++++++++++++++++++++++++++++");
			        	}
			        }
					if(count==0){
						Cookie cookie = new Cookie("JSESSIONID",request.getSession().getId().toString());
						cookie.setMaxAge(30*24*60*60);
						resp.addCookie(cookie);
						//System.out.println("执行放cookie++++++++++++++++++++++++++++++++++++++++++++");
					}
					data.put("userId",  principal.getId());
					data.put("nickName",  principal.getName());
					data.put("phoneNum",  principal.getLoginName());
				}else{
					return ErrorInfo.error(10,"", ErrorInfo.LogonFailed);
				}
			}
			return ErrorInfo.success(data);
		} catch(IncorrectCredentialsException e) {
			return ErrorInfo.error(11,"", ErrorInfo.UserNamePasswordError);
		} catch(LockedAccountException e) {
			return ErrorInfo.error(12,"", ErrorInfo.UserLocking);
		} catch(DisabledAccountException e) {
			return ErrorInfo.error(10,"", ErrorInfo.LogonFailed);
		} catch(UnknownAccountException e) {
			return ErrorInfo.error(13,"", ErrorInfo.UserNone);
		} catch(UnauthorizedException e) {
			return ErrorInfo.error(10,"", ErrorInfo.LogonFailed);
		}catch (IOException e) {
			return ErrorInfo.error(10,"", ErrorInfo.LogonFailed);
		}
	}
	
	/**
	 * 用户退出
	 * @param request
	 * @param sessionid
	 * @param resp
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="cancel",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public JSONObject cancel(HttpServletRequest request,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			HttpSession session = request.getSession();
			session.removeAttribute(SessionVariable.USER_ID);
			session.removeAttribute(SessionVariable.USER_NAME);
			session.setMaxInactiveInterval(0);
			Cookie cookie = new Cookie("JSESSIONID",null);
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(14,"", ErrorInfo.ExitFailure);
		}
	}
	/**
	 * 用户修改密码
	 * @param request
	 * @param resp
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="modifyPassword",method = RequestMethod.POST,consumes = "application/json;charset=utf-8",produces = "application/json")
	@ResponseBody
	public JSONObject modifyPassword(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			String phoneNum=mapData.get("phoneNum");
			//判断手机号是否存在
			if(StringUtils.isEmpty(phoneNum)){
				return ErrorInfo.error(1,"phoneNum", ErrorInfo.ParaNull);
			}
			String verifycode=mapData.get("SMSverifycode");
			//判断验证码是否存在
			if(StringUtils.isEmpty(verifycode)){
				return ErrorInfo.error(1,"verifycode", ErrorInfo.ParaNull);
			}
			String oldPassword=mapData.get("oldPassword");
			//判断验证码是否存在
			if(StringUtils.isEmpty(verifycode)){
				return ErrorInfo.error(1,"oldPassword", ErrorInfo.ParaNull);
			}
			String newPassword=mapData.get("newPassword");
			//判断验证码是否存在
			if(StringUtils.isEmpty(verifycode)){
				return ErrorInfo.error(1,"newPassword", ErrorInfo.ParaNull);
			}
			Map<String, String> codeInfo = (Map<String, String>)request.getSession().getAttribute("SMS_"+phoneNum);
			boolean result = false;
			System.out.println(codeInfo);
			if (codeInfo != null) {
				// 获取来自请求的验证码
				String reqCode = verifycode;
				// 获取当前系统时间的毫秒数
				long curTime = System.currentTimeMillis();
				// 获取保存在session的验证码
				String savedCode = codeInfo.get("code");
				// 获取验证码发送时间的毫秒数
				long sendTime = Long.parseLong(codeInfo.get("sendTime"));
				if (reqCode.equals(savedCode)) {
					long timeDiff = curTime - sendTime;
					if (timeDiff < Global.CODE_VALID_TIME) {
						result = true;
					}
				}
			}
			if(!result){
				return ErrorInfo.error(8,"", ErrorInfo.VerifycodeInvalid);
			}
			User user = UserUtils.getUser();
			if (SystemService.validatePassword(oldPassword, user.getPassword())){
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
			}else{
				return ErrorInfo.error(32,"", ErrorInfo.OldPasswordError);
			}
			HttpSession session = request.getSession();
			session.removeAttribute(SessionVariable.USER_ID);
			session.removeAttribute(SessionVariable.USER_NAME);
			session.setMaxInactiveInterval(0);
			Cookie cookie = new Cookie("JSESSIONID",null);
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(31,"", ErrorInfo.ModifyPasswordError);
		}
	}
	/**
	 * 用户密码重置
	 * @param request
	 * @param mapData
	 * @param resp
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="resetPassword",method = RequestMethod.POST,consumes = "application/json;charset=utf-8",produces = "application/json")
	@ResponseBody
	public JSONObject resetPassword(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String phoneNum=mapData.get("phoneNum");
			//判断手机号是否存在
			if(StringUtils.isEmpty(phoneNum)){
				return ErrorInfo.error(1,"phoneNum", ErrorInfo.ParaNull);
			}
			String verifycode=mapData.get("SMSverifycode");
			//判断验证码是否存在
			if(StringUtils.isEmpty(verifycode)){
				return ErrorInfo.error(1,"SMSverifycode", ErrorInfo.ParaNull);
			}
			String newPassword=mapData.get("newPassword");
			//判断验证码是否存在
			if(StringUtils.isEmpty(verifycode)){
				return ErrorInfo.error(1,"newPassword", ErrorInfo.ParaNull);
			}
			Map<String, String> codeInfo = (Map<String, String>)request.getSession().getAttribute("SMS_"+phoneNum);
			boolean result = false;
			System.out.println(codeInfo);
			if (codeInfo != null) {
				// 获取来自请求的验证码
				String reqCode = verifycode;
				// 获取当前系统时间的毫秒数
				long curTime = System.currentTimeMillis();
				// 获取保存在session的验证码
				String savedCode = codeInfo.get("code");
				// 获取验证码发送时间的毫秒数
				long sendTime = Long.parseLong(codeInfo.get("sendTime"));
				if (reqCode.equals(savedCode)) {
					long timeDiff = curTime - sendTime;
					if (timeDiff < Global.CODE_VALID_TIME) {
						result = true;
					}
				}
			}
			if(!result){
				return ErrorInfo.error(8,"", ErrorInfo.VerifycodeInvalid);
			}
			User user = systemService.getUserByLoginName(phoneNum);
			if(user==null){
				return ErrorInfo.error(34,"", ErrorInfo.USER_DOES_NOT_EXIST);
			}
			systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
			HttpSession session = request.getSession();
			session.removeAttribute(SessionVariable.USER_ID);
			session.removeAttribute(SessionVariable.USER_NAME);
			session.setMaxInactiveInterval(0);
			Cookie cookie = new Cookie("JSESSIONID",null);
			cookie.setMaxAge(0);
			resp.addCookie(cookie);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(33,"", ErrorInfo.ResetPasswordError);
		}
	}
	
}
