package com.tisco.modules.news.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tisco.modules.comment.entity.NewsComment;
import com.tisco.modules.comment.service.NewsCommentService;
import com.tisco.modules.news.entity.Article;
import com.tisco.modules.news.entity.AuditComment;
import com.tisco.modules.news.service.ArticleService;
import com.tisco.modules.news.service.AuditCommentService;
import com.weeln.common.config.Global;
import com.weeln.common.persistence.Page;
import com.weeln.common.web.BaseController;
import com.weeln.modules.sys.utils.UserUtils;

@Controller
@RequestMapping(value = "${adminPath}/cms/audit")
public class AuditCommentController extends BaseController {
	@Autowired
	private AuditCommentService commentService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewsCommentService newsCommentService;
	
	@ModelAttribute
	public AuditComment get(@RequestParam(required=false) String id) {
		AuditComment comment = null;
		
		if (StringUtils.isNoneEmpty(id)) {
			comment = commentService.get(id);
		} 
		
		if (comment == null){
			comment = new AuditComment();
		}
		
		return comment;
	} 
	
	@RequestMapping(value = "/save")
	public String doAudit(Article article, Model model, 
			RedirectAttributes redirectAttributes) {
		
		// 保存审核信息
		AuditComment comments = article.getAuditComments();
		
		// 设置当前用户为审核人
		comments.setAuditor(UserUtils.getUser());
		
		// 设置审核日期
		comments.setAuditedTime(new Date());
		
		commentService.save(comments);
		
		// 更新文章状态
		articleService.updateArticleStatus(article);
		
		return "redirect:" + Global.getAdminPath() + "/news/article/toAudit/list?repage";
	}
	
	@RequestMapping(value = "/show/{cmsId}")
	public String showAuditComment(@PathVariable("cmsId")String cmsId, 
				HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<AuditComment> page = commentService.findPage(new Page<AuditComment>(request, response),  new AuditComment(cmsId));
		
		model.addAttribute("page", page);
		
		return "modules/news/article/d_article_audited_record";
	}
	
	@RequiresPermissions("article:comments:audit")
	@RequestMapping("/comment/{commId}")
	public String showAuditPage(@PathVariable("commId")String commId, Model model) {
		NewsComment newsComment = newsCommentService.get(commId);
		model.addAttribute("newsComment", newsComment);
		
		return "modules/news/comment/d_news_comment_audit";
	}
	
	@RequestMapping("comment/save")
	public String doAudit(AuditComment auditComment) {
		NewsComment comment = new NewsComment();
		comment.setId(auditComment.getCmsId());
		comment.setStatus(auditComment.getResult());
		comment.setUpdateDate(new Date());
		
		newsCommentService.update(comment);
		
		commentService.save(auditComment);
		
		return "redirect:" + Global.getAdminPath() + "/news/comment/toaudit/list?repage";
	}
	
}
