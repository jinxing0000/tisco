/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.dao;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.tisco.modules.news.entity.PictureNews;

/**
 * 图片新闻DAO接口
 * @author Ancle
 * @version 2016-10-12
 */
@MyBatisDao
public interface PictureNewsDao extends CrudDao<PictureNews> {

	
}