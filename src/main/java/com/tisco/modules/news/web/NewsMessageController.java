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
import com.tisco.modules.news.entity.NewsMessage;
import com.tisco.modules.news.entity.NewsType;
import com.tisco.modules.news.service.NewsMessageService;
import com.tisco.modules.news.service.NewsTypeService;

/**
 * 新闻类型Controller
 * @author Ancle
 * @version 2016-10-05
 */
@Controller
@RequestMapping(value = "${adminPath}/news/message")
public class NewsMessageController extends BaseController {

	@Autowired
	private NewsMessageService newsMessageService;
	
	@ModelAttribute
	public NewsMessage get(@RequestParam(required=false) String id) {
		NewsMessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = newsMessageService.get(id);
		}
		if (entity == null){
			entity = new NewsMessage();
		}
		return entity;
	}
	
	/**
	 * 推送消息列表页面
	 */
	@RequiresPermissions("news:newsMessage:list")
	@RequestMapping(value = {"list", ""})
	public String list(NewsMessage newsMessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<NewsMessage> page = newsMessageService.findPage(new Page<NewsMessage>(request, response), newsMessage); 
		model.addAttribute("page", page);
		return "modules/news/message/MessageList";
	}
	
	

}