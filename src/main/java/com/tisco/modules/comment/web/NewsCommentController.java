/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.comment.web;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.tisco.modules.comment.entity.NewsComment;
import com.tisco.modules.comment.service.NewsCommentService;
import com.tisco.modules.news.entity.AuditComment;
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
 * 评论Controller
 * @author Ancle
 * @version 2016-10-06
 */
@Controller
@RequestMapping(value = "${adminPath}/news/comment")
public class NewsCommentController extends BaseController {

	@Autowired
	private NewsCommentService newsCommentService;
	
	@ModelAttribute
	public NewsComment get(@RequestParam(required=false) String id) {
		NewsComment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = newsCommentService.get(id);
		}
		if (entity == null){
			entity = new NewsComment();
		}
		return entity;
	}
	
	/**
	 * 评论列表页面
	 */
	@RequiresPermissions("comment:tb_news_comment:list")
	@RequestMapping(value = {"list", ""})
	public String list(NewsComment newsComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		newsComment.setStatus("1");
		Page<NewsComment> page = newsCommentService.findPage(new Page<NewsComment>(request, response), newsComment); 
		model.addAttribute("page", page);
		return "modules/news/comment/w_news_comments";
	}
	
	/**
	 * 评论列表页面
	 */
	@RequiresPermissions("comment:tb_news_comment:list")
	@RequestMapping(value = {"/toaudit/list", ""})
	public String toAuditList(NewsComment newsComment, HttpServletRequest request, HttpServletResponse response, Model model) {
		newsComment.setStatus("0");
		Page<NewsComment> page = newsCommentService.findPage(new Page<NewsComment>(request, response), newsComment); 
		model.addAttribute("page", page);
		
		return "modules/news/comment/w_toaudit_comments";
	}

	/**
	 * 查看，增加，编辑评论表单页面
	 */
	@RequiresPermissions(value={"comment:tb_news_comment:view","comment:tb_news_comment:add","comment:tb_news_comment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(NewsComment tb_news_comment, Model model) {
		model.addAttribute("tb_news_comment", tb_news_comment);
		return "modules/news/comment/d_news_comment_form";
	}

	/**
	 * 保存评论
	 */
	@RequestMapping(value = "save")
	public String save(NewsComment newsComment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, newsComment)){
			return form(newsComment, model);
		}
		
		newsComment.setCommentator(UserUtils.getUser());
		newsComment.setIsAnon("0");
		
		if(!newsComment.getIsNewRecord()){//编辑表单保存
			NewsComment t = newsCommentService.get(newsComment.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(newsComment, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			newsCommentService.save(t);//保存
		}else{//新增表单保存
			newsCommentService.save(newsComment);//保存
		}
		addMessage(redirectAttributes, "保存评论成功");
		return "redirect:"+Global.getAdminPath()+"/news/comment/?repage";
	}
	
	/**
	 * 删除评论
	 */
	@RequiresPermissions("comment:tb_news_comment:del")
	@RequestMapping(value = "delete")
	public String delete(NewsComment tb_news_comment, RedirectAttributes redirectAttributes) {
		newsCommentService.delete(tb_news_comment);
		addMessage(redirectAttributes, "删除评论成功");
		return "redirect:"+Global.getAdminPath()+"/news/comment/toaudit/list";
	}
	
	/**
	 * 批量删除评论
	 */
	@RequiresPermissions("comment:tb_news_comment:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			newsCommentService.delete(newsCommentService.get(id));
		}
		addMessage(redirectAttributes, "删除评论成功");
		return "redirect:"+Global.getAdminPath()+"/news/comment/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("comment:tb_news_comment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(NewsComment tb_news_comment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "评论"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<NewsComment> page = newsCommentService.findPage(new Page<NewsComment>(request, response, -1), tb_news_comment);
    		new ExportExcel("评论", NewsComment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出评论记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/comment/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("comment:tb_news_comment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<NewsComment> list = ei.getDataList(NewsComment.class);
			for (NewsComment tb_news_comment : list){
				try{
					newsCommentService.save(tb_news_comment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条评论记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条评论记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入评论失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/comment/?repage";
    }
	
	/**
	 * 下载导入评论数据模板
	 */
	@RequiresPermissions("comment:tb_news_comment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "评论数据导入模板.xlsx";
    		List<NewsComment> list = Lists.newArrayList(); 
    		new ExportExcel("评论数据", NewsComment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/comment/?repage";
    }
	
	
	

}