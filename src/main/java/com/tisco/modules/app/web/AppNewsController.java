package com.tisco.modules.app.web;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tisco.app.util.ErrorInfo;
import com.tisco.app.util.SessionVariable;
import com.tisco.modules.comment.entity.NewsComment;
import com.tisco.modules.comment.service.NewsCommentService;
import com.tisco.modules.favorite.entity.Favorite;
import com.tisco.modules.favorite.service.FavoriteService;
import com.tisco.modules.likes.entity.Likes;
import com.tisco.modules.likes.service.LikesService;
import com.tisco.modules.news.entity.Article;
import com.tisco.modules.news.entity.News;
import com.tisco.modules.news.entity.NewsType;
import com.tisco.modules.news.service.ArticleService;
import com.tisco.modules.news.service.NewsTypeService;
import com.weeln.common.config.Global;
import com.weeln.common.persistence.Page;
import com.weeln.common.web.BaseController;
import com.weeln.modules.sys.entity.User;

@Controller
@RequestMapping(value = "${adminPath}/app/news")
public class AppNewsController extends BaseController {
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private NewsTypeService newsTypeService;
	
	@Autowired
	private NewsCommentService newsCommentService;
	
	@Autowired
	private FavoriteService favoriteService;
	
	@Autowired
	private LikesService likesService;
	/**
	 * 获取新闻的类型
	 * @param request
	 * @param mapData
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/getNewsType",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public JSONObject getNewsType(HttpServletRequest request,HttpServletResponse resp){
		JSONObject data=new JSONObject();
		try {
			List<NewsType> list=newsTypeService.findNewsType();
			data.put("list", list);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(15,"", ErrorInfo.SelectNewsTypeError);
		}
	}
	/**
	 * 新闻列表页
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/getNews",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public JSONObject getNews(HttpServletRequest request,HttpServletResponse resp){
		JSONObject data=new JSONObject();
		try {
			//判断分页的条数是否为空
			String pageSize=request.getParameter("pageSize");
			if(StringUtils.isEmpty(pageSize)){
				return ErrorInfo.error(1,"pageSize", ErrorInfo.ParaNull);
			}
			//判断查询第几页的数据是否为空
			String pageNo=request.getParameter("pageNo");
			if(StringUtils.isEmpty(pageNo)){
				return ErrorInfo.error(1,"pageNo", ErrorInfo.ParaNull);
			}
			//判断文章类型是否为空
			String newsType=request.getParameter("newsType");
			if(StringUtils.isEmpty(newsType)){
				return ErrorInfo.error(1,"newsType", ErrorInfo.ParaNull);
			}
			String twoLevel="";
			//判断为视频文章
			if("video".equals(newsType)){
				//判断二级标题
				twoLevel=request.getParameter("twoLevel");
				if(StringUtils.isEmpty(twoLevel)){
					return ErrorInfo.error(1,"twoLevel", ErrorInfo.ParaNull);
				}
			}
			String title=request.getParameter("title");//文章标题
			if(!StringUtils.isEmpty(title)){
				title=URLDecoder.decode(title,"UTF-8");
			}
			Article article=new Article();
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			if(!StringUtils.isEmpty(userId)){
				User holder=new User();
				holder.setId(userId);
				article.setIssuer(holder);
			}
			
			// 新闻查询条件
			NewsType newType=new NewsType();
			newType.setKey(newsType);
			article.setType(newType);
			article.setTitle(title);
			article.setStatus(Global.NEWS_ISSUE_STATUS_4);
			article.setTwoLevel(twoLevel);
			
			// 分页
			Page<Article> page=new Page<Article>(request, resp);
			page.setPageSize(Integer.parseInt(pageSize));
			page.setPageNo(Integer.parseInt(pageNo));
			//page.setOrderBy("date_format(a.issued_date,'%Y-%m-%d') desc, a.sort desc");
			
			Page<Article> pageList=articleService.appFindPage(page, article);
			data.put("page", pageList);
			
			return ErrorInfo.success(data);   
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(16,"", ErrorInfo.SelectNewsError);
		}
	}
	
	/**
	 * 新版新闻列表接口
	 * @param request
	 * @param resp
	 * @return
	 */
	@RequestMapping(value="/getTiscoNews",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public JSONObject getTiscoNews(HttpServletRequest request,HttpServletResponse resp){
		JSONObject data=new JSONObject();
		try {
			//判断分页的条数是否为空
			String pageSize=request.getParameter("pageSize");
			if(StringUtils.isEmpty(pageSize)){
				return ErrorInfo.error(1,"pageSize", ErrorInfo.ParaNull);
			}
			//判断查询第几页的数据是否为空
			String pageNo=request.getParameter("pageNo");
			if(StringUtils.isEmpty(pageNo)){
				return ErrorInfo.error(1,"pageNo", ErrorInfo.ParaNull);
			}
			//判断文章类型是否为空
			String newsType=request.getParameter("newsType");
			if(StringUtils.isEmpty(newsType)){
				return ErrorInfo.error(1,"newsType", ErrorInfo.ParaNull);
			}
			String twoLevel="";
			//判断为视频文章
			if("video".equals(newsType)){
				//判断二级标题
				twoLevel=request.getParameter("twoLevel");
				if(StringUtils.isEmpty(twoLevel)){
					return ErrorInfo.error(1,"twoLevel", ErrorInfo.ParaNull);
				}
			}
			String title=request.getParameter("title");//文章标题
			if(!StringUtils.isEmpty(title)){
				title=URLDecoder.decode(title,"UTF-8");
			}
			Article article=new Article();
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			if(!StringUtils.isEmpty(userId)){
				User holder=new User();
				holder.setId(userId);
				article.setIssuer(holder);
			}
			
			// 新闻查询条件
			NewsType newType=new NewsType();
			newType.setKey(newsType);
			article.setType(newType);
			article.setTitle(title);
			article.setStatus(Global.NEWS_ISSUE_STATUS_4);
			article.setTwoLevel(twoLevel);
			
			// 分页
			Page<Article> page=new Page<Article>(request, resp);
			page.setPageSize(Integer.parseInt(pageSize));
			page.setPageNo(Integer.parseInt(pageNo));
			//page.setOrderBy("date_format(a.issued_date,'%Y-%m-%d') desc, a.sort desc");
			if("1".equals(pageNo)){
				List<Article> focusList=articleService.appTiscoFocusList(article);
				data.put("focusList", focusList);
			}
			Page<Article> newsPageList=articleService.appTiscoFindPage(page, article);
			data.put("newsPage", newsPageList);
			return ErrorInfo.success(data);   
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(16,"", ErrorInfo.SelectNewsError);
		}
	}
	
	/**
	 * 获取指定新闻的详细
	 * @param newsId
	 * @return
	 */
	@RequestMapping(value = "/article/{id}/{isPhone}",method = RequestMethod.GET,produces = "application/json")
	public String getNewsInfo(@PathVariable("id")String newsId,@PathVariable("isPhone")String isPhone, Model model) {
		// 获取新闻的阅读量
		int readingAmount = articleService.getReadingAmount(newsId);
		model.addAttribute("readingAmount", readingAmount);
		// 更新新闻的阅读量
		articleService.updateReadingAmount(newsId);
		Article article = articleService.get(newsId);
		NewsComment newsComment=new NewsComment();
		newsComment.setStatus("1");
		News news=new News();
		news.setId(newsId);
		newsComment.setNews(news);
		User holder=new User();
		newsComment.setCommentator(holder);
		model.addAttribute("article", article);
		List<NewsComment> commentList= newsCommentService.findListByNewsId(newsComment);
		model.addAttribute("commentList", commentList);
		if(article.getRecommendNews()!=null){
			List<Article> recommendNews= articleService.recommendNews(article.getRecommendNews());
			model.addAttribute("recommendNews", recommendNews);
		}
		model.addAttribute("isPhone", isPhone);
		return "modules/news/article/d_news_article_view";
	}
	
	/**
	 * 查询文章详情
	 * @param request
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/getArticleInfo",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public JSONObject getArticleInfo(HttpServletRequest request) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			System.out.println(userId+"用户编号+++@@@@@@@@@@@@@@@@@@");
			String userName = (String) request.getSession().getAttribute(SessionVariable.USER_NAME);
			String newsId=request.getParameter("newsId");
			Likes likes=new Likes();
			likes.setNewsId(newsId);
			if(StringUtils.isEmpty(newsId)){
				return ErrorInfo.error(1,"newsId", ErrorInfo.ParaNull);
			}
			if(!StringUtils.isEmpty(userId)){
				Favorite favorite=new Favorite();
				favorite.setNewsId(newsId);
				User holder=new User();
				holder.setId(userId);
				favorite.setHolder(holder);
				List<Favorite> list=favoriteService.findByNewId(favorite);
				if(list.size()==0){
					data.put("isFavorite", "0");
				}else{
					data.put("isFavorite", "1");
				}
				likes.setHolder(holder);
				// 设置新闻URL
				String newsUrl = "/app/news/article/" + newsId;
				likes.setNewsUrl(newsUrl);
				likes.setNickName(userName);
				List<Likes> listLikes=likesService.findByNewId(likes);
				if(listLikes.size()==0){
					data.put("isLiked", "0");
				}else{
					data.put("isLiked", "1");
				}
			}
			// 获取新闻的评论数
			int commentsCount = newsCommentService.getCommentCount(newsId);
			int likesCount=likesService.findCountByNewId(likes);
			Article  article=articleService.get(newsId);
			data.put("commentsCount", commentsCount);
			data.put("likesCount", likesCount);
			data.put("articleInfoUrl", "/app/news/article/"+newsId);
			data.put("article", article);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(17,"", ErrorInfo.SelectArticleInfoError);
		}
	}
	
	/**
	 * 保存评论信息
	 * @param request
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/saveComment",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject saveComment(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			String userName = (String) request.getSession().getAttribute(SessionVariable.USER_NAME);
			NewsComment newsComment=new NewsComment();
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			User holder=new User();
			holder.setId(userId);
			newsComment.setCommentator(holder);
			String comment=mapData.get("comment");
			if(StringUtils.isEmpty(comment)){
				return ErrorInfo.error(1,"comment", ErrorInfo.ParaNull);
			}
			newsComment.setComment(comment);
			String newsId=mapData.get("newsId");
			if(StringUtils.isEmpty(newsId)){
				return ErrorInfo.error(1,"newsId", ErrorInfo.ParaNull);
			}
			newsComment.setNickName(userName);
			News news=new News();
			news.setId(newsId);
			newsComment.setNews(news);
			newsComment.setStatus("0");
			newsCommentService.save(newsComment);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(18,"", ErrorInfo.SaveCommentError);
		}
	}
	/**
	 * 新闻收藏————YJX
	 * @param request
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/saveFavorite",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject saveFavorite(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			String userName = (String) request.getSession().getAttribute(SessionVariable.USER_NAME);
			Favorite favorite=new Favorite();
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			User holder=new User();
			holder.setId(userId);
			favorite.setHolder(holder);
			String newsId=mapData.get("newsId");
			if(StringUtils.isEmpty(newsId)){
				return ErrorInfo.error(1,"newsId", ErrorInfo.ParaNull);
			}
			favorite.setNewsId(newsId);
			// 设置新闻URL
			String newsUrl = "/app/news/article/" + newsId;
			favorite.setNewsUrl(newsUrl);
			favorite.setNickName(userName);
			List<Favorite> listFavorite=favoriteService.findByNewId(favorite);
			if(listFavorite.size()==0){
				favoriteService.save(favorite);
			}else{
				return ErrorInfo.error(19,"", ErrorInfo.ArticlesHaveBeenCollected);
			}
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(20,"", ErrorInfo.SaveFavoriteError);
		}
	}
	/**取消收藏
	 * 
	 * @param request
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/delFavorite",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject delFavorite(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			Favorite favorite=new Favorite();
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			User holder=new User();
			holder.setId(userId);
			favorite.setHolder(holder);
			String newsId=mapData.get("newsId");
			if(StringUtils.isEmpty(newsId)){
				return ErrorInfo.error(1,"newsId", ErrorInfo.ParaNull);
			}
			favorite.setNewsId(newsId);
			// 设置新闻URL
			String newsUrl = "/app/news/article/" + newsId;
			favorite.setNewsUrl(newsUrl);
			List<Favorite> listFavorite=favoriteService.findByNewId(favorite);
			if(listFavorite.size()==0){
				return ErrorInfo.error(21,"", ErrorInfo.NotCollected);
			}else{
				favoriteService.deleteByLogic((Favorite)listFavorite.get(0));
			}
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(22,"", ErrorInfo.SaveFavoriteError);
		}
	}
	
	
	/**
	 * 查询文章评论信息
	 * @param request
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/selectComment",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public JSONObject selectComment(HttpServletRequest request,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			String newsId=request.getParameter("newsId");
			if(StringUtils.isEmpty(userId)&&StringUtils.isEmpty(newsId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			//判断分页的条数是否为空
			String pageSize=request.getParameter("pageSize");
			if(StringUtils.isEmpty(pageSize)){
				return ErrorInfo.error(1,"pageSize", ErrorInfo.ParaNull);
			}
			//判断查询第几页的数据是否为空
			String pageNo=request.getParameter("pageNo");
			if(StringUtils.isEmpty(pageNo)){
				return ErrorInfo.error(1,"pageNo", ErrorInfo.ParaNull);
			}
			NewsComment newsComment=new NewsComment();
			newsComment.setStatus("1");
			User holder=new User();
			if(!StringUtils.isEmpty(userId)){
				holder.setId(userId);
				newsComment.setCommentator(holder);
			}
			News news=new News();
			if(!StringUtils.isEmpty(newsId)){
				news.setId(newsId);
				newsComment.setNews(news);
			}
			Page<NewsComment> page=new Page<NewsComment>(request, resp);
			page.setPageSize(Integer.parseInt(pageSize));
			page.setPageNo(Integer.parseInt(pageNo));
			page.setOrderBy("a.create_date desc");
			Page<NewsComment> pageList=newsCommentService.appFindPage(page, newsComment);
			data.put("page", pageList);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(23,"", ErrorInfo.SelectCommentError);
		}
	}
	/**
	 * 保存点赞信息
	 * @param request
	 * @param mapData
	 * @param resp
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/saveLikes",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject saveLikes(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			String userName = (String) request.getSession().getAttribute(SessionVariable.USER_NAME);
			Likes likes=new Likes();
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			User holder=new User();
			holder.setId(userId);
			likes.setHolder(holder);
			String newsId=mapData.get("newsId");
			if(StringUtils.isEmpty(newsId)){
				return ErrorInfo.error(1,"newsId", ErrorInfo.ParaNull);
			}
			likes.setNewsId(newsId);
			// 设置新闻URL
			String newsUrl = "/app/news/article/" + newsId;
			likes.setNewsUrl(newsUrl);
			likes.setNickName(userName);
			List<Likes> listLikes=likesService.findByNewId(likes);
			if(listLikes.size()==0){
				likesService.save(likes);
			}else{
				return ErrorInfo.error(19,"", ErrorInfo.ArticlesHaveBeenCollected);
			}
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(25,"", ErrorInfo.SaveLikesError);
		}
	}
	
	/**取消点赞
	 * 
	 * @param request
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/delLikes",method = RequestMethod.POST,consumes = "application/json;charset=utf-8" ,produces = "application/json")
	@ResponseBody
	public JSONObject delLikes(HttpServletRequest request,@RequestBody Map<String, String> mapData,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			Likes likes=new Likes();
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			User holder=new User();
			holder.setId(userId);
			likes.setHolder(holder);
			String newsId=mapData.get("newsId");
			if(StringUtils.isEmpty(newsId)){
				return ErrorInfo.error(1,"newsId", ErrorInfo.ParaNull);
			}
			likes.setNewsId(newsId);
			// 设置新闻URL
			String newsUrl = "/app/news/article/" + newsId;
			likes.setNewsUrl(newsUrl);
			List<Likes> listLikes=likesService.findByNewId(likes);
			if(listLikes.size()==0){
				return ErrorInfo.error(27,"", ErrorInfo.NoLikes);
			}else{
				likesService.deleteByLogic((Likes)listLikes.get(0));
			}
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(26,"", ErrorInfo.DelLikesError);
		}
	}
	
	
	/**
	 * 查询收藏的新闻列表
	 * @param request
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value="/selectFavoriteNews",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public JSONObject selectFavoriteNews(HttpServletRequest request,HttpServletResponse resp) throws JsonGenerationException, JsonMappingException, IOException{
		JSONObject data=new JSONObject();
		try {
			String userId = (String) request.getSession().getAttribute(SessionVariable.USER_ID);
			if(StringUtils.isEmpty(userId)){
				return ErrorInfo.error(5,"", ErrorInfo.Invalid);
			}
			//判断分页的条数是否为空
			String pageSize=request.getParameter("pageSize");
			if(StringUtils.isEmpty(pageSize)){
				return ErrorInfo.error(1,"pageSize", ErrorInfo.ParaNull);
			}
			//判断查询第几页的数据是否为空
			String pageNo=request.getParameter("pageNo");
			if(StringUtils.isEmpty(pageNo)){
				return ErrorInfo.error(1,"pageNo", ErrorInfo.ParaNull);
			}
			Favorite favorite=new Favorite();
			User holder=new User();
			holder.setId(userId);
			favorite.setHolder(holder);
			Page<Favorite> page=new Page<Favorite>(request, resp);
			page.setPageSize(Integer.parseInt(pageSize));
			page.setPageNo(Integer.parseInt(pageNo));
			page.setOrderBy("a.create_date desc");
			Page<Favorite> pageList=favoriteService.appFindPage(page, favorite);
			data.put("page", pageList);
			return ErrorInfo.success(data);
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorInfo.error(24,"", ErrorInfo.SelectFavoriteNewsError);
		}
	}
	
	
	
}
