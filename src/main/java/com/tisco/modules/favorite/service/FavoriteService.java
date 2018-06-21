/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.favorite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tisco.modules.favorite.dao.FavoriteDao;
import com.tisco.modules.favorite.entity.Favorite;
import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;

/**
 * 收藏Service
 * @author Ancle
 * @version 2016-10-05
 */
@Service
@Transactional(readOnly = true)
public class FavoriteService extends CrudService<FavoriteDao, Favorite> {
	@Autowired
	private FavoriteDao favoriteDao;

	public Favorite get(String id) {
		return super.get(id);
	}
	
	public List<Favorite> findList(Favorite favorite) {
		return super.findList(favorite);
	}
	
	public Page<Favorite> findPage(Page<Favorite> page, Favorite favorite) {
		return super.findPage(page, favorite);
	}
	
	@Transactional(readOnly = false)
	public void save(Favorite favorite) {
		super.save(favorite);
	}
	
	@Transactional(readOnly = false)
	public void delete(Favorite favorite) {
		super.delete(favorite);
	}
	
	public Favorite find(Favorite favorite) {
		return favoriteDao.find(favorite);
	}
	@Transactional(readOnly = false)
	public List<Favorite> findByNewId(Favorite favorite){
		return favoriteDao.findByNewId(favorite);
	}
	
}