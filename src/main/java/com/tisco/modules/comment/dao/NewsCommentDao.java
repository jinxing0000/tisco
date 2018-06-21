/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.comment.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tisco.modules.comment.entity.NewsComment;
import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;

/**
 * 评论DAO接口
 * @author Ancle
 * @version 2016-10-06
 */
@MyBatisDao
public interface NewsCommentDao extends CrudDao<NewsComment> {

	public int getCommentsCount(@Param("newsId")String newsId);
	/**
	 * 查询文章评论
	 * @param newsComment
	 * @return
	 */
	public List<NewsComment> findListByNewsId(NewsComment newsComment);
}