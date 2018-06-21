/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.apk.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tisco.app.util.FTPUtil;
import com.tisco.app.util.PropertiesReader;
import com.tisco.modules.apk.entity.Apk;
import com.tisco.modules.apk.service.ApkService;
import com.weeln.common.config.Global;
import com.weeln.common.persistence.Page;
import com.weeln.common.utils.MyBeanUtils;
import com.weeln.common.utils.StringUtils;
import com.weeln.common.web.BaseController;
import com.weeln.modules.sys.entity.User;
import com.weeln.modules.sys.utils.UserUtils;

/**
 * apk Controller
 * @author Ancle
 * @version 2016-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/news/apk")
public class ApkController extends BaseController {
	@Autowired
	private ApkService apkService;
	
	
	@ModelAttribute
	public Apk get(@RequestParam(required=false) String id) {
		Apk entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = apkService.get(id);
		}
		if (entity == null){
			entity = new Apk();
		}
		return entity;
	}
	
	/**
	 * apk 列表页面
	 */
	@RequiresPermissions("news:apk:list")
	@RequestMapping(value = {"list", ""})
	public String list(Apk apk, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Apk> page = apkService.findPage(new Page<Apk>(request, response), apk); 
		model.addAttribute("page", page);
		return "modules/news/apk/apkList";
	}

	/**
	 * 查看，增加，编辑apk版本信息表单页面
	 */
	@RequiresPermissions(value={"news:apk:view","news:apk:add","news:apk:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Apk apk, Model model) {
		model.addAttribute("apk", apk);
		return "modules/news/apk/apkForm";
	}
	
	/**
	 * 新版客户端发布页面
	 * @param apk
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value = "news:apk:add")
	@RequestMapping(value = "/new")
	public String addForm(Apk apk, Model model) {
		model.addAttribute("apk", apk);
		
		return "modules/news/apk/w_new_apk";
	}
	
	/**
	 * 保存apk版本信息
	 */
	@RequiresPermissions(value={"news:apk:add","news:apk:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Apk apk, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, apk)){
			return form(apk, model);
		}
		apk.setUploadTime(new Date());
		User user = UserUtils.getUser();
		apk.setUploadUserId(user.getId());
		apk.setDelFlag("0");
		if(!apk.getIsNewRecord()){//编辑表单保存
			Apk t = apkService.get(apk.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(apk, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			apkService.save(t);//保存
		}else{//新增表单保存
			apkService.save(apk);//保存
		}
		addMessage(redirectAttributes, "保存apk版本信息成功");
		
		String flag = request.getParameter("flag");
		if (flag != null && "alone".equals(flag)) {
			return "redirect:"+Global.getAdminPath()+"/news/apk/new?repage";
		}
		
		return "redirect:"+Global.getAdminPath()+"/news/apk/list?repage";
	}
	
	/**
	 * 删除apk版本信息
	 */
	@RequiresPermissions("news:apk:del")
	@RequestMapping(value = "delete")
	public String delete(Apk apk, RedirectAttributes redirectAttributes) {
		apkService.deleteByLogic(apk);
		addMessage(redirectAttributes, "删除apk版本信息成功");
		return "redirect:"+Global.getAdminPath()+"/news/apk/?repage";
	}
	
	/**
	 * 上传apk文件
	 * @param request
	 * @param response
	 * @param feedback_file
	 * @return
	 */
	@ResponseBody
	@RequestMapping("uploadApk")
	public List<String> uploadFile(HttpServletRequest request,HttpServletResponse response,@RequestParam("uploadApk") CommonsMultipartFile[] uploadApk){
		List<String> list=new ArrayList<String>();
		try {
			response.setContentType("text/html;charset=GBK");    
			request.setCharacterEncoding("GBK");
			//单个上传文件大小的上限    
			int maxSize=20*1024*1024;  
			//循环文件
			for(int i = 0;i<uploadApk.length;i++){
				long upFileSize=uploadApk[i].getSize();
				if(upFileSize>maxSize){      
					list.add("error");
					list.add("上传文件失败！！");
					return list;            
				}
				PropertiesReader preader = new PropertiesReader("appversion.properties");
				File path = new File(preader.getProperty("loadApk"));
                //判断上传文件的保存目录是否存在
				if (!path.exists() && !path.isDirectory()) {
				    //创建目录
					path.mkdirs();
				}
				String fileName=uploadApk[i].getOriginalFilename();
				String filetype=fileName.split("\\.")[1];
				InputStream in = (InputStream) uploadApk[i].getInputStream();
				byte[] buffer = null;
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            byte[] b = new byte[10240];
	            int n;  
	            while ((n = in.read(b)) != -1)
	            {  
	                bos.write(b, 0, n);  
	            }  
	            in.close();
	            bos.close();  
	            buffer = bos.toByteArray();
	            String fname = FTPUtil.getJdFilename()+"."+filetype;
				String foldername = "apk/"+FTPUtil.getJdgafilename();
	            boolean result = FTPUtil.saveinFTP(foldername,fname,buffer);
				
				
				
				
				
//	            String filePath="\\"+UUID.randomUUID().toString()+"."+filetype;
//	            FileOutputStream out = new FileOutputStream(preader.getProperty("loadApk")+filePath);
//	            //创建一个缓冲区
//	            byte buffer[] = new byte[1024];
//                //判断输入流中的数据是否已经读完的标识
//	            int len = 0;
//	            //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
//	            while((len=in.read(buffer))>0){
//	            //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
//	                out.write(buffer, 0, len);
//                }
//	            //关闭输入流
//                in.close();
//                //关闭输出流
//	            out.close();
				//成功上传
			    list.add("success");
				list.add("/"+FTPUtil.getJdgafilename()+"/"+fname);
				list.add(fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			list.add("error");
			list.add("上传文件失败！！");
		}
		return list;
	}
}