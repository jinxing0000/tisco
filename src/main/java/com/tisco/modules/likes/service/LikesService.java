/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.likes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tisco.modules.favorite.dao.FavoriteDao;
import com.tisco.modules.favorite.entity.Favorite;
import com.tisco.modules.likes.dao.LikesDao;
import com.tisco.modules.likes.entity.Likes;
import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;

/**
 * 点赞Service
 * @author Ancle
 * @version 2016-10-05
 */
@Service
@Transactional(readOnly = true)
public class LikesService extends CrudService<LikesDao, Likes> {
	@Autowired
	private LikesDao likesDao;

	public Likes get(String id) {
		return super.get(id);
	}
	/**
	 * 保存点赞信息
	 * @param likes
	 */
	@Transactional(readOnly = false)
	public void save(Likes likes) {
		super.save(likes);
	}
	/**
	 * 分页查询点赞信息
	 * @param page
	 * @param likes
	 * @return
	 */
	public Page<Likes> appFindPage(Page<Likes>  page,Likes likes){
		return super.appFindPage(page, likes);
	}
	/**
	 * 取消点赞信息
	 * @param likes
	 */
	@Transactional(readOnly = false)
	public void deleteByLogic(Likes likes){
		super.deleteByLogic(likes);
	}
	@Transactional(readOnly = false)
	public List<Likes> findByNewId(Likes likes){
		return likesDao.findByNewId(likes);
	}
	/**
	 * 按照文章查询点赞次数
	 * @param likes
	 * @return
	 */
	public int findCountByNewId(Likes likes){
		return likesDao.findCountByNewId(likes);
	}
}