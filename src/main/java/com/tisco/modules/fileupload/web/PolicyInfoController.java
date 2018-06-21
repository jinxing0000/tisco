package com.tisco.modules.fileupload.web;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.weeln.common.web.BaseController;
import com.tisco.modules.fileupload.entity.FileUpload;
import com.tisco.modules.fileupload.service.FileUploadService;

/**
 * 政策法规文件上传下载管理
 * 
 * @author 安丰伟
 * @time 2013-8-23 下午4:26:27
 * @Explanation
 */
@Controller
@RequestMapping(value = "${adminPath}/policyInfo")
public class PolicyInfoController extends BaseController{

	@Autowired
	private FileUploadService fileUploadService;

	/**
	 * 文件上传
	 * 
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/uploadAndAdd", method = RequestMethod.POST)
	@ResponseBody
	public String uploadAndAdd(HttpServletRequest request, ModelMap modelMap, @RequestParam MultipartFile fileInput) throws Exception {
		String id = fileUploadService.saveAttachment(request, fileInput);
		return id;
	}
	 
	/**
	 * 下载方法
	 * 
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/download")
	public void download(final HttpServletResponse response, HttpServletRequest request, String id) throws Exception {
		FileUpload fileUpload = fileUploadService.findUniqueByProperty("id", id);
		String fileName = URLEncoder.encode(fileUpload.getName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		response.setHeader("Content-Length", String.valueOf(new File(fileUpload.getPath()).length()));
		response.setContentType("application/octet-stream;charset=UTF-8");
		try {
			fileUploadService.download(fileUpload.getPath(), response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/deleteFile")
	@ResponseBody
	public void deleteFile(@RequestParam String id) {
		try {
			fileUploadService.deleteFile(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping("updateFileForSort")
	public List updateFileForSort(HttpServletRequest request,HttpServletResponse response){
		List list=new ArrayList();
		try {
			FileUpload fileUpload=new FileUpload();
			fileUpload.setSort(Integer.parseInt(request.getParameter("sort")));
			fileUpload.setId(request.getParameter("id"));
			fileUploadService.updateForSort(fileUpload);
			list.add("success");
			list.add("修改排序成功");
		} catch (Exception e) {
			e.printStackTrace();
			list.add("error");
			list.add("修改排序号失败！！");
		}
		return list;
	}
 
}
