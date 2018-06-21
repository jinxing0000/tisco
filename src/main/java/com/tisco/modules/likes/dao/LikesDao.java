/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.likes.dao;


import java.util.List;

import com.tisco.modules.likes.entity.Likes;
import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;

/**
 * 点赞DAO接口
 * @author Ancle
 * @version 2016-10-05
 */
@MyBatisDao
public interface LikesDao extends CrudDao<Likes> {
	/**
	 * 按照文章和用户查询
	 * @param likes
	 * @return
	 */
	public List<Likes> findByNewId(Likes likes);
	/**
	 * 按照文章查询点赞次数
	 * @param likes
	 * @return
	 */
	public int findCountByNewId(Likes likes);

}