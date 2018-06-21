/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.favorite.dao;

import java.util.List;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.tisco.modules.favorite.entity.Favorite;

/**
 * 收藏DAO接口
 * @author Ancle
 * @version 2016-10-05
 */
@MyBatisDao
public interface FavoriteDao extends CrudDao<Favorite> {

	public Favorite find(Favorite favorite);
	/**
	 * 按照用户和新闻id查询收藏信息
	 * @param favorite
	 * @return
	 */
	public List<Favorite> findByNewId(Favorite favorite);
}