/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.weeln.common.utils.DateUtils;
import com.weeln.common.utils.MyBeanUtils;
import com.weeln.common.config.Global;
import com.weeln.common.persistence.Page;
import com.weeln.common.web.BaseController;
import com.weeln.common.utils.StringUtils;
import com.weeln.common.utils.excel.ExportExcel;
import com.weeln.common.utils.excel.ImportExcel;
import com.tisco.modules.news.entity.NewsType;
import com.tisco.modules.news.service.NewsTypeService;

/**
 * 新闻类型Controller
 * @author Ancle
 * @version 2016-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/news/type")
public class NewsTypeController extends BaseController {

	@Autowired
	private NewsTypeService newsTypeService;
	
	@ModelAttribute
	public NewsType get(@RequestParam(required=false) String id) {
		NewsType entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = newsTypeService.get(id);
		}
		if (entity == null){
			entity = new NewsType();
		}
		return entity;
	}
	
	/**
	 * 新闻类型列表页面
	 */
	@RequiresPermissions("news:newsType:list")
	@RequestMapping(value = {"list", ""})
	public String list(NewsType newsType, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NewsType> page = newsTypeService.findPage(new Page<NewsType>(request, response), newsType); 
		model.addAttribute("page", page);
		return "modules/news/type/newsTypeList";
	}

	/**
	 * 查看，增加，编辑新闻类型表单页面
	 */
	@RequiresPermissions(value={"news:newsType:view","news:newsType:add","news:newsType:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(NewsType newsType, Model model) {
		model.addAttribute("newsType", newsType);
		return "modules/news/type/newsTypeForm";
	}

	/**
	 * 保存新闻类型
	 */
	@RequiresPermissions(value={"news:newsType:add","news:newsType:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(NewsType newsType, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, newsType)){
			return form(newsType, model);
		}
		if(!newsType.getIsNewRecord()){//编辑表单保存
			NewsType t = newsTypeService.get(newsType.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(newsType, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			newsTypeService.save(t);//保存
		}else{//新增表单保存
			newsTypeService.save(newsType);//保存
		}
		addMessage(redirectAttributes, "保存新闻类型成功");
		return "redirect:"+Global.getAdminPath()+"/news/type/list?repage";
	}
	
	/**
	 * 删除新闻类型
	 */
	@RequiresPermissions("news:newsType:del")
	@RequestMapping(value = "delete")
	public String delete(NewsType newsType, RedirectAttributes redirectAttributes) {
		newsTypeService.delete(newsType);
		addMessage(redirectAttributes, "删除新闻类型成功");
		return "redirect:"+Global.getAdminPath()+"/news/type/?repage";
	}
	
	/**
	 * 批量删除新闻类型
	 */
	@RequiresPermissions("news:newsType:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			newsTypeService.delete(newsTypeService.get(id));
		}
		addMessage(redirectAttributes, "删除新闻类型成功");
		return "redirect:"+Global.getAdminPath()+"/news/type/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("news:newsType:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(NewsType newsType, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "新闻类型"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<NewsType> page = newsTypeService.findPage(new Page<NewsType>(request, response, -1), newsType);
    		new ExportExcel("新闻类型", NewsType.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出新闻类型记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/type/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("news:newsType:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<NewsType> list = ei.getDataList(NewsType.class);
			for (NewsType newsType : list){
				try{
					newsTypeService.save(newsType);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条新闻类型记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条新闻类型记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入新闻类型失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/type/?repage";
    }
	
	/**
	 * 下载导入新闻类型数据模板
	 */
	@RequiresPermissions("news:newsType:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "新闻类型数据导入模板.xlsx";
    		List<NewsType> list = Lists.newArrayList(); 
    		new ExportExcel("新闻类型数据", NewsType.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/type/list?repage";
    }
	
	
	

}