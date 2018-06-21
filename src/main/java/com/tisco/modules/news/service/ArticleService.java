/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.push.PushResult;

import com.weeln.common.config.Global;
import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;
import com.weeln.common.utils.IdGen;
import com.tisco.app.util.JdpushUtil;
import com.tisco.modules.news.entity.Article;
import com.tisco.modules.news.entity.NewsMessage;
import com.tisco.modules.news.entity.NewsType;
import com.tisco.modules.fileupload.entity.FileUpload;
import com.tisco.modules.fileupload.service.FileUploadService;
import com.tisco.modules.news.dao.ArticleDao;

/**
 * 文章新闻Service
 * @author Ancle
 * @version 2016-10-04
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {
	@Autowired
	private FileUploadService  fileUploadService;
	@Autowired
	private NewsMessageService newsMessageService;

	public Article get(String id) {
		
		Article article = super.get(id);
		
		List<FileUpload> files = findUploadedFiles(id);
		if(files!=null){
			article.setFileUploads(files);
		}
		return article;
	}
	
	public List<Article> findList(Article article) {
		return super.findList(article);
	}
	
	public Page<Article> findPage(Page<Article> page, Article article) {
		return super.findPage(page, article);
	}
	
	@Transactional(readOnly = false)
	private void saveAttachments(Article acticle) {
		
		Map<String, Object> map =new HashMap<String, Object>();
		String ids=acticle.getPicId()+acticle.getAudioId()+acticle.getVideoId();
//		String policyinfoid=super.id;
//		if(policyinfoid==null){
//			policyinfoid=acticle.getId();
//		}
		String policyinfoid=acticle.getId();
		//判断是否有附件
		if(!"".equals(ids)){
			ids=ids.substring(0, ids.length()-1);
			String str[]=ids.split(",");
			List<String> list=new ArrayList<String>();
			for(int i=0;i<str.length;i++){
				list.add(str[i]);
			}
			map.put("ids", list);
			map.put("policyinfoid", policyinfoid);
			fileUploadService.updatePol(map);
			fileUploadService.updatePolicyinfoid(map);
		}
	}
	
	@Transactional(readOnly = false)
	public void save(Article article) {
		if(article.getId()==null||"".equals(article.getId())){
			article.setIsNewRecord(true);
			article.setId(IdGen.uuid());
		}
//		// 保存新闻内容
		super.save(article);
//		// 保存附件
		saveAttachments(article);
		
	}
	
	@Transactional(readOnly = false)
	public void updateArticleStatus(Article article) {
		dao.updateArticleStatus(article);
		
		if (Global.NEWS_ISSUE_STATUS_4.equals(article.getStatus()) && "1".equals(article.getIsPushMsg())) {
			NewsType  newsType=article.getType();
			Map<String, String> data=new HashMap<String, String>();
	     	data.put("newsId", article.getId());
			JdpushUtil jdpush=new JdpushUtil("de0e004c153a41742da11bae","891764136151212519ca08df"); 
			PushResult pushResult=jdpush.sendPushRadioBroadcast(data, newsType.getName(), article.getTitle());
			if(pushResult!=null){
				NewsMessage newsMessage=new NewsMessage();
		     	newsMessage.setNewsId(super.id);
		     	newsMessage.setNewsType(newsType.getId());
		     	newsMessage.setTitle(article.getTitle());
		     	newsMessageService.save(newsMessage);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void updateArticleContentType(Article article) {
		dao.updateArticleContentType(article);
	}
	
	@Transactional(readOnly = false)
	public void delete(Article article) {
		super.delete(article);
	}
	
	/**
	 * 获取附件信息
	 * @param id
	 * @return
	 */
	public List<FileUpload> findUploadedFiles(String id) {
		return fileUploadService.findFilesByFId(id);
	}
	
	@Transactional(readOnly = true)
	public int getReadingAmount(String newsId) {
		int readingAmount = 0;
		
		Integer amount = dao.getReadingAmount(newsId);
		if (amount != null) {
			readingAmount = amount.intValue();
		}
		
		return readingAmount;
	}
	
	@Transactional(readOnly = false)
	public void updateReadingAmount(String newsId) {
		if (dao.getReadingAmount(newsId) == null) {
			dao.insertOne(newsId, 0);
		}
		
		dao.updateReadingAmount(newsId);
	}
	/**
	 * 查询我收藏的新闻
	 * @param article
	 * @return
	 */
	public List<Article> findListByFavorite(Article article){
		return dao.findListByFavorite(article);
	}
	
	public List<Article> recommendNews(String ids){
		Map<String, Object> map=new HashMap<String, Object>();
		String str[]=ids.split(",");
		List<String> list=new ArrayList<String>();
		for(int i=0;i<str.length;i++){
			list.add(str[i]);
		}
		map.put("ids", list);
		return dao.recommendNews(map);
	}
	
	/**
	 * 新版查询新闻列表
	 * @param page
	 * @param article
	 * @return
	 */
	public Page<Article> appTiscoFindPage(Page<Article> page, Article article) {
		article.setPage(page);
		page.setList(dao.appTiscoFindPage(article));
		return page;
	}
	
	public List<Article> appTiscoFocusList( Article article) {
		return dao.appTiscoFocusList(article);
	}
}