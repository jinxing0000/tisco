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
import com.tisco.modules.fileupload.entity.FileUpload;
import com.tisco.modules.news.entity.PictureNews;
import com.tisco.modules.news.service.PictureNewsService;
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
 * 图片新闻Controller
 * @author Ancle
 * @version 2016-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/news/picture")
public class PictureNewsController extends BaseController {

	@Autowired
	private PictureNewsService pictureNewsService;
	
	@ModelAttribute
	public PictureNews get(@RequestParam(required=false) String id) {
		PictureNews entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = pictureNewsService.get(id);
		}
		if (entity == null){
			entity = new PictureNews();
		}
		return entity;
	}
	
	/**
	 * 图片新闻列表页面
	 */
	@RequiresPermissions("picture:pictureNews:list")
	@RequestMapping(value = {"list", ""})
	public String list(PictureNews pictureNews, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PictureNews> page = pictureNewsService.findPage(new Page<PictureNews>(request, response), pictureNews); 
		model.addAttribute("page", page);
		return "modules/picture/pictureNewsList";
	}

	/**
	 * 查看，增加，编辑图片新闻表单页面
	 */
	@RequiresPermissions(value={"picture:pictureNews:view","picture:pictureNews:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(PictureNews pictureNews, Model model) {
		
		if (StringUtils.isNoneBlank(pictureNews.getId())) {
			List<FileUpload> files = pictureNewsService.findUploadedFiles(pictureNews.getId());
			model.addAttribute("files", files);
		}
		
		model.addAttribute("pictureNews", pictureNews);
		
		return "modules/picture/pictureNewsForm";
	}
	
	/**
	 * 发布图片新闻
	 * @param pictureNews
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value="picture:pictureNews:add")
	@RequestMapping(value = "new")
	public String issueNew(PictureNews pictureNews, Model model) {
		// 设置当前用户为发布人
		pictureNews.setIssuer(UserUtils.getUser());
		
		model.addAttribute("pictureNews", pictureNews);
		
		return "modules/picture/w_picture_news_add";
	}

	/**
	 * 保存图片新闻
	 */
	@RequiresPermissions(value={"picture:pictureNews:add","picture:pictureNews:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(PictureNews pictureNews, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, pictureNews)){
			return form(pictureNews, model);
		}
		if(!pictureNews.getIsNewRecord()){//编辑表单保存
			PictureNews t = pictureNewsService.get(pictureNews.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(pictureNews, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			pictureNewsService.save(t);//保存
		}else{//新增表单保存
			pictureNewsService.save(pictureNews);//保存
		}
		addMessage(redirectAttributes, "保存图片新闻成功");
		return "redirect:"+Global.getAdminPath()+"/news/picture/new?repage";
	}
	
	/**
	 * 删除图片新闻
	 */
	@RequiresPermissions("picture:pictureNews:del")
	@RequestMapping(value = "delete")
	public String delete(PictureNews pictureNews, RedirectAttributes redirectAttributes) {
		pictureNewsService.delete(pictureNews);
		addMessage(redirectAttributes, "删除图片新闻成功");
		return "redirect:"+Global.getAdminPath()+"/news/picture/list?repage";
	}
	
	/**
	 * 批量删除图片新闻
	 */
	@RequiresPermissions("picture:pictureNews:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			pictureNewsService.delete(pictureNewsService.get(id));
		}
		addMessage(redirectAttributes, "删除图片新闻成功");
		return "redirect:"+Global.getAdminPath()+"/news/picture/list?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("picture:pictureNews:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(PictureNews pictureNews, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "图片新闻"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<PictureNews> page = pictureNewsService.findPage(new Page<PictureNews>(request, response, -1), pictureNews);
    		new ExportExcel("图片新闻", PictureNews.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出图片新闻记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/picture/list?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("picture:pictureNews:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<PictureNews> list = ei.getDataList(PictureNews.class);
			for (PictureNews pictureNews : list){
				try{
					pictureNewsService.save(pictureNews);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条图片新闻记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条图片新闻记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入图片新闻失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/picture/list?repage";
    }
	
	/**
	 * 下载导入图片新闻数据模板
	 */
	@RequiresPermissions("picture:pictureNews:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "图片新闻数据导入模板.xlsx";
    		List<PictureNews> list = Lists.newArrayList(); 
    		new ExportExcel("图片新闻数据", PictureNews.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/picture/list?repage";
    }
	
	
	

}