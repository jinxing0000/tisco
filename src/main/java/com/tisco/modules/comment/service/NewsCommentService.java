/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;
import com.tisco.modules.comment.entity.NewsComment;
import com.tisco.modules.comment.dao.NewsCommentDao;

/**
 * 评论Service
 * @author Ancle
 * @version 2016-10-06
 */
@Service
@Transactional(readOnly = true)
public class NewsCommentService extends CrudService<NewsCommentDao, NewsComment> {

	public NewsComment get(String id) {
		return super.get(id);
	}
	
	public List<NewsComment> findList(NewsComment newsComment) {
		return super.findList(newsComment);
	}
	
	public Page<NewsComment> findPage(Page<NewsComment> page, NewsComment newsComment) {
		return super.findPage(page, newsComment);
	}
	
	@Transactional(readOnly = false)
	public void save(NewsComment newsComment) {
		super.save(newsComment);
	}
	
	@Transactional(readOnly = false)
	public void delete(NewsComment newsComment) {
		super.delete(newsComment);
	}
	
	@Transactional(readOnly = true)
	public int getCommentCount(String newsId) {
		return dao.getCommentsCount(newsId);
	}
	/**
	 * 查询文章的评论信息
	 * @param newsComment
	 * @return
	 */
	public List<NewsComment> findListByNewsId(NewsComment newsComment){
		return dao.findListByNewsId(newsComment);
	}
	
	@Transactional(readOnly = false)
	public void update(NewsComment comment) {
		dao.update(comment);
	}
}