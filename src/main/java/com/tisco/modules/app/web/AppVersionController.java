package com.tisco.modules.app.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fusesource.hawtbuf.ByteArrayInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tisco.app.util.ErrorInfo;
import com.tisco.app.util.FTPUtil;
import com.tisco.app.util.PropertiesReader;
import com.tisco.modules.apk.entity.Apk;
import com.tisco.modules.apk.service.ApkService;
import com.weeln.common.utils.StringUtils;
import com.weeln.common.web.BaseController;

@Controller
@RequestMapping(value = "${adminPath}/app")
public class AppVersionController extends BaseController {
	
	@Autowired
	private ApkService apkService;
	
	/**
	 * 获取apk的最新信息
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="version",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject version(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			//查询apk最新信息
			Apk apk=apkService.findApk();
			if(apk!=null){
				String path = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":" + request.getServerPort()
						+ path + "/";
				data.put("version_code",apk.getApkVersionCode());
				data.put("version_name", apk.getApkVersion());
				data.put("version_download", basePath+"a/app/downloadApk");
				data.put("version_message", apk.getApkMessage());
				data.put("file_name", apk.getApkName()+apk.getApkVersion());
			}
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(28,"", ErrorInfo.ApkError);
		}
	}
	/**
	 * 下载apk
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("downloadApk") 
	public String downloadFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//附件的ID
		try {
		//查询apk最新信息
		Apk apk=apkService.findApk();
		if(apk!=null){
			
				//文件名
				String fileName =apk.getApkName()+apk.getApkVersion()+".apk";
				//解决乱码
				String agent = request.getHeader("USER-AGENT");
			        if (null != agent){  
			            if (-1 != agent.indexOf("Firefox")) {//Firefox  
			            	fileName = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(fileName.getBytes("UTF-8"))))+ "?=";  
			            }else if (-1 != agent.indexOf("Chrome")) {//Chrome  
			            	fileName = new String(fileName.getBytes(), "ISO8859-1");  
			            } else {//IE7+  
			            	fileName = java.net.URLEncoder.encode(fileName, "UTF-8");  
			            	fileName = StringUtils.replace(fileName, "+", "%20");//替换空格 
			            }
			     }
				// 设置response的编码方式
				response.setContentType("application/x-msdownload");
				// 设置附加文件名
				response.setHeader("Content-Disposition","attachment;filename="+fileName);
				
//				PropertiesReader preader = new PropertiesReader("appversion.properties");
//				System.out.println(preader.getProperty("loadApk"));
				// 读出文件到i/o流
//				InputStream fis =new FileInputStream("d:\\FTP\\apk\\TiscoNewsApp-debug.apk");
				System.out.println(FTPUtil.getApkPath()+apk.getApkPath());
				//获取FTP文件流
				InputStream apkIs=FTPUtil.downloadFile("apk"+apk.getApkPath());
				//重复利用文件流
				ByteArrayOutputStream baos = new ByteArrayOutputStream();  
				byte[] buffer = new byte[1024];
				int len=0;
				while ((len = apkIs.read(buffer)) > -1) {
					baos.write(buffer, 0, len);
				}
				baos.flush();
				//新建下载apk流
				InputStream fis = new ByteArrayInputStream(baos.toByteArray());
				//新建获取apkSize流
				InputStream SizeIs = new ByteArrayInputStream(baos.toByteArray());
				//获取apk文件大小
				int count = 0;
				byte[] bb = new byte[1024];
				int size=0;
				while ((size = SizeIs.read(bb)) > 0) {
					count += size;
				}
				response.setContentLength(count);
				OutputStream os = response.getOutputStream();
				byte[] b = new byte[1024];
				int length=0;
				while ((length = fis.read(b)) > 0) {
					os.write(b, 0, length);
				}
				 // 这里主要关闭。
				os.close();
				SizeIs.close();
				fis.close();
				baos.close();
		       }
			} catch (Exception e) {
				e.printStackTrace();
			}
		return null;
	}
}
