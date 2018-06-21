/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringEscapeUtils;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.tisco.app.util.FTPUtil;
import com.tisco.app.util.FileUploadUtil;
import com.tisco.app.util.PropertiesReader;
import com.tisco.modules.fileupload.entity.FileUpload;
import com.tisco.modules.fileupload.service.FileUploadService;
import com.tisco.modules.news.entity.Article;
import com.tisco.modules.news.entity.NewsType;
import com.tisco.modules.news.service.ArticleService;
import com.tisco.modules.news.service.NewsTypeService;
import com.weeln.common.config.Global;
import com.weeln.common.persistence.Page;
import com.weeln.common.utils.DateUtils;
import com.weeln.common.utils.StringUtils;
import com.weeln.common.utils.excel.ExportExcel;
import com.weeln.common.utils.excel.ImportExcel;
import com.weeln.common.web.BaseController;
import com.weeln.modules.sys.utils.UserUtils;

/**
 * 文章新闻Controller
 * @author Ancle
 * @version 2016-10-04
 */
@Controller
@RequestMapping(value = "${adminPath}/news/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewsTypeService newsTypeService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@ModelAttribute
	public Article get(@RequestParam(required=false) String id) {
		Article entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = articleService.get(id);
		}
		if (entity == null){
			entity = new Article();
		}
		return entity;
	}
	
	/**
	 * 文章列表页面
	 */
	@RequiresPermissions("news:article:list")
	@RequestMapping(value = {"list", ""})
	public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 取所有已发布的新闻
		article.setStatus(Global.NEWS_ISSUE_STATUS_4);
		
		if ((article.getType() == null)
				|| (StringUtils.isEmpty(article.getType().getKey()))) {
			article.setType(null);
		}
		
		if (StringUtils.isEmpty(article.getTitle())) {
			article.setTitle(null);
		}
		
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article); 
		List<NewsType> newsType = newsTypeService.findList(new NewsType());
		model.addAttribute("newsType", newsType);
		model.addAttribute("page", page);
		
		return "modules/news/article/articleList";
	}
	/**
	 * 选择新闻列表页面
	 * @param article
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("news:article:list")
	@RequestMapping(value = {"queryList", ""})
	public String queryList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		// 取所有已发布的新闻
		article.setStatus(Global.NEWS_ISSUE_STATUS_4);
		
		if ((article.getType() == null)
				|| (StringUtils.isEmpty(article.getType().getKey()))) {
			article.setType(null);
		}
		
		if (StringUtils.isEmpty(article.getTitle())) {
			article.setTitle(null);
		}
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article); 
		List<NewsType> newsType = newsTypeService.findList(new NewsType());
		model.addAttribute("newsType", newsType);
		model.addAttribute("page", page);
		
		return "modules/news/article/queryArticleList";
	}
	
	/**
	 * 文章列表页面
	 */
	@RequiresPermissions("news:article:list")
	@RequestMapping(value = {"/mini/list", ""})
	public String miniList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
		String status = article.getStatus();
		
		if ((article.getType() == null)
				|| (StringUtils.isEmpty(article.getType().getKey()))) {
			article.setType(null);
		}
		
		if (StringUtils.isEmpty(article.getTitle())) {
			article.setTitle(null);
		}
		
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article); 
		
		List<NewsType> newsType = newsTypeService.findList(new NewsType());
		model.addAttribute("newsType", newsType);
		
		model.addAttribute("page", page);
		
		String viewName = "";
		if (status.equals(Article.ARTICLE_ISSUE_STATUS_4)) {
			viewName = "w_mini_issued_article";
		} else if (status.equals(Article.ARTICLE_ISSUE_STATUS_1)) {
			viewName = "w_mini_in-auditing_articles";
		} else if (status.equals(Article.ARTICLE_ISSUE_STATUS_0)) {
			viewName = "w_mini_draft_articles";
		} 
		return "modules/news/article/" + viewName;
	}
	
	@RequiresPermissions("news:article:toAudit")
	@RequestMapping(value = "/toAudit/list")
	public String listToAuditArticles(Article article, HttpServletRequest request, 
			HttpServletResponse response, Model model) {
		article.setStatus(Article.ARTICLE_ISSUE_STATUS_1);
		
		if ((article.getType() == null)
				|| (StringUtils.isEmpty(article.getType().getKey()))) {
			article.setType(null);
		}
		
		if (StringUtils.isEmpty(article.getTitle())) {
			article.setTitle(null);
		}
		
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article); 
		
		List<NewsType> newsType = newsTypeService.findList(new NewsType());
		model.addAttribute("newsType", newsType);
		
		model.addAttribute("page", page);
		
		return "modules/news/article/w_in_auditing_articles";
	}
	
	@RequestMapping(value = "/toAudit/show")
	public String doAudit(Article article, Model model) {
		model.addAttribute("article", article);
		
		return "modules/news/article/d_news_article_audit";
	}
	
	@RequiresPermissions("news:article:self")
	@RequestMapping(value = "/self")
	public String listSelfArtils(Article article, HttpServletRequest request, 
			HttpServletResponse response, Model model) {

		article.setIssuer(UserUtils.getUser());
		
		/**
		 * 设置多个状态查询条件
		 * 审核中、被退回、已审核(通过)
		 */
		String multiStatus = "('" + Global.NEWS_ISSUE_STATUS_1 + "', '"
				+ Global.NEWS_ISSUE_STATUS_2 + "', '"
				+ Global.NEWS_ISSUE_STATUS_3 + "', '"
				+ Global.NEWS_ISSUE_STATUS_4 + "')";
		article.setMultiStatus(multiStatus);
		
		if ((article.getType() == null)
				|| (StringUtils.isEmpty(article.getType().getKey()))) {
			article.setType(null);
		}
		
		if (StringUtils.isEmpty(article.getTitle())) {
			article.setTitle(null);
		}
		
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article); 
		
		List<NewsType> newsType = newsTypeService.findList(new NewsType());
		model.addAttribute("newsType", newsType);
		
		model.addAttribute("page", page);
		
		return "modules/news/article/w_self_articles";
	}
	
	@RequiresPermissions("news:article:draft")
	@RequestMapping(value = "/draft/list")
	public String listDraftArticles(Article article, HttpServletRequest request, 
			HttpServletResponse response, Model model) {
		// 设置发布人
		article.setIssuer(UserUtils.getUser());
		// 设置状态条件：草稿
		article.setStatus(Global.NEWS_ISSUE_STATUS_0);
		
		if ((article.getType() == null)
				|| (StringUtils.isEmpty(article.getType().getKey()))) {
			article.setType(null);
		}
		
		if (StringUtils.isEmpty(article.getTitle())) {
			article.setTitle(null);
		}
		
		Page<Article> page = articleService.findPage(new Page<Article>(request, response), article); 
		
		List<NewsType> newsType = newsTypeService.findList(new NewsType());
		model.addAttribute("newsType", newsType);
		
		model.addAttribute("page", page);
		
		return "modules/news/article/w_draft_articles";
	}

	/**
	 * 查看，增加，编辑文章表单页面
	 */
	@RequiresPermissions(value={"news:article:view", "news:article:edit", "news:article:modify"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Article article, @RequestParam(value="flag", required=false)String flag, Model model) {
		article.setContent(StringEscapeUtils.unescapeHtml4(article.getContent()));
		model.addAttribute("article", article); 
		
        List<Article> recommendNews= articleService.recommendNews(article.getRecommendNews());
		
		model.addAttribute("recommendNews", recommendNews);
		
		List<NewsType> newsType = newsTypeService.findListForArticle(new NewsType());
		model.addAttribute("newsType", newsType);
		
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		String imgPath=preader.getProperty("imgPath");
		model.addAttribute("imgPath", imgPath);
		
		if (StringUtils.isEmpty(flag)) {
			flag = "";
		}
		model.addAttribute("flag", flag);
		
		return "modules/news/article/articleForm";
	}
	/**
	 * 管理员直接修改文章
	 * @param article
	 * @param flag
	 * @param model
	 * @return
	 */
	@RequiresPermissions(value={"news:article:view", "news:article:edit", "news:article:modify"},logical=Logical.OR)
	@RequestMapping(value = "modify")
	public String modify(Article article, @RequestParam(value="flag", required=false)String flag, Model model) {
		article.setContent(StringEscapeUtils.unescapeHtml4(article.getContent()));
		model.addAttribute("article", article); 
		
		List<NewsType> newsType = newsTypeService.findListForArticle(new NewsType());
		model.addAttribute("newsType", newsType);
		
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		String imgPath=preader.getProperty("imgPath");
		model.addAttribute("imgPath", imgPath);
		
		if (StringUtils.isEmpty(flag)) {
			flag = "";
		}
		model.addAttribute("flag", flag);
		
		return "modules/news/article/article_form_modify";
	}
	
	/**
	 * 增加文章表单页面
	 */
	@RequiresPermissions(value={"news:article:add"},logical=Logical.OR)
	@RequestMapping(value = "new")
	public String doNew(Article article, Model model) {
		// 设置发布时间为当前时间
		article.setIssuedDate(new Date());
		
		List<NewsType> newsType = newsTypeService.findListForArticle(new NewsType());
		model.addAttribute("newsType", newsType);
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		String imgPath=preader.getProperty("imgPath");
		model.addAttribute("article", article);
		model.addAttribute("imgPath", imgPath);
		
		return "modules/news/article/w_article_add";
	}
	
	/**
	 * 查看，增加，编辑文章表单页面
	 */
	@RequiresPermissions(value={"news:article:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String doView(Article article, Model model) {
		List<NewsType> newsType = newsTypeService.findList(new NewsType());
		model.addAttribute("newsType", newsType);
		
		model.addAttribute("article", article);
		return "modules/news/article/d_news_article_view";
	}
	
	@RequiresPermissions(value={"news:article:view"},logical=Logical.OR)
	@RequestMapping(value = "queryPic")
	public String queryPic(Article article, Model model) {
		Article newArticle = get(article.getId());
		model.addAttribute("article", newArticle);
		
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		String imgPath=preader.getProperty("imgPath");
		model.addAttribute("imgPath", imgPath);
		
		return "modules/news/article/d_news_article_query_pic";
	}
	
	@RequiresPermissions(value={"news:article:view"},logical=Logical.OR)
	@RequestMapping(value = "queryVideo")
	public String queryVideo(Article article, Model model) {
		Article newArticle = get(article.getId());
		model.addAttribute("article", newArticle);
		
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		String filePath=preader.getProperty("filePath");
		model.addAttribute("filePath", filePath);
		
		return "modules/news/article/d_news_article_query_video";
	}

	/**
	 * 保存文章
	 */
	@RequiresPermissions(value={"news:article:add", "news:article:edit", "news:article:modify"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Article article, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws Exception{
		
		if (!beanValidator(model, article)){
			return form(article, "", model);
		}
		
		if(article.getIsNewRecord()){//编辑表单保存
			// 设置发布者为当前用户
			article.setIssuer(UserUtils.getUser());
		}
		articleService.save(article);//保存
		
		addMessage(redirectAttributes, "保存文章成功");
		
		
		// 设置返回URL
		String reqFlag = (String) request.getParameter("flag");
		if (StringUtils.isNoneEmpty(reqFlag)) {
			if (reqFlag.equals("draft")) {
				return "redirect:" + Global.getAdminPath() + "/news/article/draft/list";
			}
			
			if (reqFlag.equals("issued")) {
				return "redirect:" + Global.getAdminPath() + "/news/article/list";
			}
			
			if (reqFlag.equals("self")) {
				return "redirect:" + Global.getAdminPath() + "/news/article/self";
			}
		}
		return "redirect:"+Global.getAdminPath()+"/news/article/new";
	}
	
	@RequiresPermissions("news:article:issue")
	@RequestMapping(value = "issue")
	public String issueArticle(Article article) {
		// 设置新闻状态为发布
		article.setStatus(Global.NEWS_ISSUE_STATUS_4);
		articleService.updateArticleStatus(article);
		
		return "redirect:" + Global.getAdminPath() + "/news/article/self?repage";
	}
	
	@RequiresPermissions("news:article:revoke")
	@RequestMapping(value = "revoke")
	public String revokeFocusArticle(Article article) {
		
		article.setContentType("");
		
		articleService.updateArticleContentType(article);
		
		return "redirect:" + Global.getAdminPath() + "/news/article/list?repage";
	}
	
	/**
	 * 删除文章
	 */
	@RequiresPermissions(value = {"news:article:del", "news:article:delete"}, logical=Logical.OR)
	@RequestMapping(value = "delete")
	public String delete(Article article, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		articleService.delete(article);
		addMessage(redirectAttributes, "删除文章成功");
		
		String flag = request.getParameter("flag");
		if (StringUtils.isBlank(flag)) {
			flag = "";
		}
		
		String returnUrl = "/news/article/list?repage";
		
		switch(flag) {
		case "draft":
			returnUrl = "/news/article/draft/list?repage";
			break;
		case "self":
			returnUrl = "/news/article/self?repage";
			break;
		default:
			returnUrl = "/news/article/list?repage";	
		}
		
		return "redirect:" + Global.getAdminPath() + returnUrl;
	}
	
	/**
	 * 批量删除文章
	 */
	@RequiresPermissions(value = {"news:article:del", "news:article:delete"}, logical=Logical.OR)
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		
		for(String id : idArray){
			articleService.delete(new Article(id));
		}
		
		addMessage(redirectAttributes, "删除文章成功");
		
		String flag = request.getParameter("flag");
		String returnUrl = "/news/article/list?repage";
		if (StringUtils.isNoneBlank(flag) && flag.equals("draft")) {
			returnUrl = "/news/article/draft/list?repage";
		}
		
		return "redirect:" + Global.getAdminPath() + returnUrl;
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("news:article:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Article article, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文章"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Article> page = articleService.findPage(new Page<Article>(request, response, -1), article);
    		new ExportExcel("文章", Article.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出文章记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/article/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("news:article:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Article> list = ei.getDataList(Article.class);
			for (Article article : list){
				try{
					articleService.save(article);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条文章记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条文章记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入文章失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/article/?repage";
    }
	
	/**
	 * 下载导入文章数据模板
	 */
	@RequiresPermissions("news:article:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文章数据导入模板.xlsx";
    		List<Article> list = Lists.newArrayList(); 
    		new ExportExcel("文章数据", Article.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/news/article/?repage";
    }
	
	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @param pic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="loadPic",produces = "application/json")
	public List uploadFile(HttpServletRequest request,HttpServletResponse response,@RequestParam("pic") CommonsMultipartFile[] pic){
		List list=new ArrayList();
		try {
			int sort=Integer.parseInt(request.getParameter("sort"));
			response.setContentType("text/html;charset=GBK");    
			request.setCharacterEncoding("GBK");
			//单个上传文件大小的上限    
			int maxSize=20*1024*1024;  
			//循环文件
			for(int i = 0;i<pic.length;i++){
				long upFileSize=pic[i].getSize();
				if(upFileSize>maxSize){      
					list.add("error");
					list.add("上传文件失败！！");
					return list;            
				}
//				PropertiesReader preader = new PropertiesReader("appversion.properties");
//				File path = new File(preader.getProperty("loadPicPath")+"\\"+new SimpleDateFormat("yyyy").format(new Date())+new SimpleDateFormat("MM").format(new Date())+"\\"+new SimpleDateFormat("dd").format(new Date()));
//                //判断上传文件的保存目录是否存在
//				if (!path.exists() && !path.isDirectory()) {
//				    //创建目录
//					path.mkdirs();
//				}
				String fileName=pic[i].getOriginalFilename();
				String filetype=fileName.split("\\.")[1];
				if((!"png".equals(filetype))&&(!"jpg".equals(filetype))){
					list.add("error");
					list.add("图片格式为：png或者jpg");
					return list;
				}
				InputStream in = (InputStream) pic[i].getInputStream();
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
				String foldername = "image/"+FTPUtil.getJdgafilename();
	            boolean result = FTPUtil.saveinFTP(foldername,fname,buffer);
				
				
//	            String filePath="\\"+new SimpleDateFormat("yyyy").format(new Date())+new SimpleDateFormat("MM").format(new Date())+"\\"+new SimpleDateFormat("dd").format(new Date())+"\\"+UUID.randomUUID().toString()+"."+filetype;
//	            FileOutputStream out = new FileOutputStream(preader.getProperty("loadPicPath")+filePath);
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
	            FileUpload fileUpload=new FileUpload();
	            fileUpload.setName(fileName);
	            fileUpload.setType(filetype);
	            fileUpload.setPath("/"+FTPUtil.getJdgafilename()+"/"+fname);
	            //fileUpload.setPath(filePath.replace("\\", "/"));
	            fileUpload.setSort(sort);
	            String id=fileUploadService.insertFile(fileUpload);
	            fileUpload.setId(id);
			    list.add("success");
				list.add(fileUpload);
			}
		} catch (Exception e) {
			e.printStackTrace();
			list.add("error");
			list.add("上传文件失败！！");
		}
		return list;
	}
	
	/**
	 * 上传音频文件
	 * @param request
	 * @param response
	 * @param pic
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="loadFile",produces = "application/json")
	public List loadFile(HttpServletRequest request,HttpServletResponse response,@RequestParam("file") CommonsMultipartFile[] file){
		List list=new ArrayList();
		try {
			int sort=Integer.parseInt(request.getParameter("sort"));
			response.setContentType("text/html;charset=GBK");    
			request.setCharacterEncoding("UTF-8");
			//单个上传文件大小的上限    
			int maxSize=1000*1024*1024;  
			//循环文件
			for(int i = 0;i<file.length;i++){
				long upFileSize=file[i].getSize();
				if(upFileSize>maxSize){      
					list.add("error");
					list.add("上传文件失败！！");
					return list;            
				}
//				PropertiesReader preader = new PropertiesReader("appversion.properties");
//				File path = new File(preader.getProperty("loadFilePath")+"\\"+new SimpleDateFormat("yyyy").format(new Date())+new SimpleDateFormat("MM").format(new Date())+"\\"+new SimpleDateFormat("dd").format(new Date()));
//                //判断上传文件的保存目录是否存在
//				if (!path.exists() && !path.isDirectory()) {
//				    //创建目录
//					path.mkdirs();
//				}
				String fileName=file[i].getOriginalFilename();
				String filetype=fileName.split("\\.")[1];
					if(!"mp3".equals(filetype)){
						list.add("error");
						list.add("音频格式为：mp3！！");
						return list;
					}
				InputStream in = (InputStream) file[i].getInputStream();
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
				String foldername = "file/"+FTPUtil.getJdgafilename();
	            boolean result = FTPUtil.saveinFTP(foldername,fname,buffer);
				
				
				
				
				
				
//	            String filePath="\\"+new SimpleDateFormat("yyyy").format(new Date())+new SimpleDateFormat("MM").format(new Date())+"\\"+new SimpleDateFormat("dd").format(new Date())+"\\"+UUID.randomUUID().toString()+"."+filetype;
//	            FileOutputStream out = new FileOutputStream(preader.getProperty("loadFilePath")+filePath);
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
//	            long whenLong=FileUploadUtil.getMp3WhenLong(preader.getProperty("loadFilePath")+filePath);
	            String filePath=FTPUtil.getFilePath();
	            long whenLong=FileUploadUtil.getHttpMp3WhenLong(filePath+"/"+FTPUtil.getJdgafilename()+"/"+fname);
	            FileUpload fileUpload=new FileUpload();
	            fileUpload.setName(fileName);
	            fileUpload.setType(filetype);
	            fileUpload.setPath("/"+FTPUtil.getJdgafilename()+"/"+fname);
//	            fileUpload.setPath(filePath.replace("\\", "/"));
	            fileUpload.setSort(sort);
	            fileUpload.setWhenLong(whenLong);
	            String id=fileUploadService.insertFile(fileUpload);
	            fileUpload.setId(id);
			    list.add("success");
				list.add(fileUpload);
			}
		} catch (Exception e) {
			e.printStackTrace();
			list.add("error");
			list.add("上传文件失败！！");
		}
		return list;
	}
	/**
	 * 上传视频文件
	 * @param request
	 * @param response
	 * @param file
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="loadVideoFile",produces = "application/json")
	public List loadVideoFile(HttpServletRequest request,HttpServletResponse response,@RequestParam("file_voide") CommonsMultipartFile[] file){
		List list=new ArrayList();
		try {
			int sort=Integer.parseInt(request.getParameter("sort"));
			response.setContentType("text/html;charset=GBK");    
			request.setCharacterEncoding("UTF-8");
			//单个上传文件大小的上限    
			int maxSize=1000*1024*1024;  
			//循环文件
			for(int i = 0;i<file.length;i++){
				long upFileSize=file[i].getSize();
				if(upFileSize>maxSize){      
					list.add("error");
					list.add("上传文件失败！！");
					return list;            
				}
//				PropertiesReader preader = new PropertiesReader("appversion.properties");
//				File path = new File(preader.getProperty("loadFilePath")+"\\"+new SimpleDateFormat("yyyy").format(new Date())+new SimpleDateFormat("MM").format(new Date())+"\\"+new SimpleDateFormat("dd").format(new Date()));
//                //判断上传文件的保存目录是否存在
//				if (!path.exists() && !path.isDirectory()) {
//				    //创建目录
//					path.mkdirs();
//				}
				String fileName=file[i].getOriginalFilename();
				String filetype=fileName.split("\\.")[1];
					if(!"mp4".equals(filetype)){
						list.add("error");
						list.add("视频格式为：mp4！！");
						return list;
					}
				InputStream in = (InputStream) file[i].getInputStream();
				String fname = FTPUtil.getJdFilename()+"."+filetype;
	            String filePath=FTPUtil.getloadFilePath();
	            File path = new File(filePath);
//              //判断上传文件的保存目录是否存在
				if (!path.exists() && !path.isDirectory()) {
				    //创建目录
					path.mkdirs();
				}
				FileOutputStream out = new FileOutputStream(filePath+"\\"+fname);
				byte[] buffer = null;
	            ByteArrayOutputStream bos = new ByteArrayOutputStream();
	            byte[] b = new byte[10240];
	            int n;  
	            while ((n = in.read(b)) != -1)
	            {  
	                bos.write(b, 0, n);
	                out.write(b, 0, n);
	            }
	            bos.close();  
	            out.close();
	            buffer = bos.toByteArray();
				String foldername = "file/"+FTPUtil.getJdgafilename();
	            boolean result = FTPUtil.saveinFTP(foldername,fname,buffer);
	            result=FTPUtil.saveinFTPVideo(foldername,fname,buffer);
				
				
				
				
//	            String filePath="\\"+new SimpleDateFormat("yyyy").format(new Date())+new SimpleDateFormat("MM").format(new Date())+"\\"+new SimpleDateFormat("dd").format(new Date())+"\\"+UUID.randomUUID().toString()+"."+filetype;
				//成功上传\
//	            long whenLong=FileUploadUtil.getMp4WhenLong(preader.getProperty("loadFilePath")+filePath);
	            long whenLong=FileUploadUtil.getMp4WhenLong(filePath+"\\"+fname);
	            FileUpload fileUpload=new FileUpload();
	            fileUpload.setName(fileName);
	            fileUpload.setType(filetype);
	            fileUpload.setPath("/"+FTPUtil.getJdgafilename()+"/"+fname);
	            fileUpload.setSort(sort);
	            fileUpload.setWhenLong(whenLong);
	            String id=fileUploadService.insertFile(fileUpload);
	            fileUpload.setId(id);
			    list.add("success");
				list.add(fileUpload);
			}
		} catch (Exception e) {
			e.printStackTrace();
			list.add("error");
			list.add("上传文件失败！！");
		}
		return list;
	}
	
	@RequestMapping(value = "/attachment/del")
	public String deleteAttachment(String fileId, String filePath) {
		FileUpload fileUpload = new FileUpload();
		
		if (StringUtils.isNoneEmpty(fileId)) {
			String[] fileIds = fileId.split(",");
			for (String id : fileIds) {
				fileUpload.setId(id);
				fileUploadService.delete(fileUpload);
			}
		}
		
		if (StringUtils.isNoneEmpty(filePath)) {
			String[] filePaths = filePath.split(",");
			PropertiesReader preader = new PropertiesReader("appversion.properties");
			String rootPath = preader.getProperty("loadFilePath");
			String path = "";
			for (int i = 0; i < filePaths.length; i++) {
				path = filePaths[i].replace("/", File.separator);
				File file = new File(rootPath + path);
				if (file.exists()) {
					file.delete();
				}
			}
		}
		
		return "success";
	}
}