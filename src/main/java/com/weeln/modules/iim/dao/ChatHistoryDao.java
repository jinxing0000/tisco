/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.iim.dao;

import java.util.List;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.weeln.modules.iim.entity.ChatHistory;

/**
 * 聊天记录DAO接口
 * @author weeln
 * @version 2015-12-29
 */
@MyBatisDao
public interface ChatHistoryDao extends CrudDao<ChatHistory> {
	
	
	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<ChatHistory> findLogList(ChatHistory entity);
	
	
	public int findUnReadCount(ChatHistory chatHistory);
	
}