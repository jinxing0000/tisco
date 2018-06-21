/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.favorite.web;

import java.util.Date;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.tisco.modules.favorite.entity.Favorite;
import com.tisco.modules.favorite.service.FavoriteService;
import com.weeln.common.config.Global;
import com.weeln.common.persistence.Page;
import com.weeln.common.utils.DateUtils;
import com.weeln.common.utils.MyBeanUtils;
import com.weeln.common.utils.StringUtils;
import com.weeln.common.utils.excel.ExportExcel;
import com.weeln.common.utils.excel.ImportExcel;
import com.weeln.common.web.BaseController;
import com.weeln.modules.sys.utils.UserUtils;

/**
 * 收藏Controller
 * @author Ancle
 * @version 2016-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/news/favorite")
public class FavoriteController extends BaseController {

	@Autowired
	private FavoriteService favoriteService;
	
	@ModelAttribute
	public Favorite get(@RequestParam(required=false) String id) {
		Favorite entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = favoriteService.get(id);
		}
		if (entity == null){
			entity = new Favorite();
		}
		return entity;
	}
	
	/**
	 * 收藏列表页面
	 */
	@RequiresPermissions("favorite:favorite:list")
	@RequestMapping(value = {"list", ""})
	public String list(Favorite favorite, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Favorite> page = favoriteService.findPage(new Page<Favorite>(request, response), favorite); 
		model.addAttribute("page", page);
		return "modules/favorite/favoriteList";
	}

	/**
	 * 查看，增加，编辑收藏表单页面
	 */
	@RequiresPermissions(value={"favorite:favorite:view","favorite:favorite:add","favorite:favorite:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Favorite favorite, Model model) {
		model.addAttribute("favorite", favorite);
		return "modules/favorite/favoriteForm";
	}

	/**
	 * 保存收藏
	 */
	@RequiresPermissions(value={"favorite:favorite:add","favorite:favorite:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Favorite favorite, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, favorite)){
			return form(favorite, model);
		}
		if(!favorite.getIsNewRecord()){//编辑表单保存
			Favorite t = favoriteService.get(favorite.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(favorite, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			favoriteService.save(t);//保存
		}else{//新增表单保存
			favoriteService.save(favorite);//保存
		}
		addMessage(redirectAttributes, "保存收藏成功");
		return "redirect:"+Global.getAdminPath()+"/news/favorite/?repage";
	}
	
	/**
	 * 收藏
	 * @param favorite
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "toCollect")
	public String doCollecting(Favorite favorite, Model model) {
		String result = "false";
		
		favorite.setHolder(UserUtils.getUser());
		favorite.setCreateDate(new Date());
		
		favoriteService.save(favorite);
		
		if (StringUtils.isNoneBlank(favorite.getId())) {
			result = "true";
		}
		
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "cancel")
	public String doCancel(Favorite favorite, Model model) {
		String result = "false";
		
		favorite.setHolder(UserUtils.getUser());
		
		favoriteService.delete(favorite);
		
		result = "true";
		
		return result;
	}
	
	/**
	 * 删除收藏
	 */
	@RequiresPermissions("favorite:favorite:del")
	@RequestMapping(value = "delete")
	public String delete(Favorite favorite, RedirectAttributes redirectAttributes) {
		favoriteService.delete(favorite);
		addMessage(redirectAttributes, "删除收藏成功");
		return "redirect:"+Global.getAdminPath()+"/news/favorite/?repage";
	}
	
	/**
	 * 批量删除收藏
	 */
	@RequiresPermissions("favorite:favorite:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			favoriteService.delete(favoriteService.get(id));
		}
		addMessage(redirectAttributes, "删除收藏成功");
		return "redirect:"+Global.getAdminPath()+"/news/favorite/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("favorite:favorite:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Favorite favorite, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "收藏"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Favorite> page = favoriteService.findPage(new Page<Favorite>(request, response, -1), favorite);
    		new ExportExcel("收藏", Favorite.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出收藏记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/favorite/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("favorite:favorite:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Favorite> list = ei.getDataList(Favorite.class);
			for (Favorite favorite : list){
				try{
					favoriteService.save(favorite);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条收藏记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条收藏记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入收藏失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/favorite/?repage";
    }
	
	/**
	 * 下载导入收藏数据模板
	 */
	@RequiresPermissions("favorite:favorite:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "收藏数据导入模板.xlsx";
    		List<Favorite> list = Lists.newArrayList(); 
    		new ExportExcel("收藏数据", Favorite.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/favorite/?repage";
    }
	
	
	

}