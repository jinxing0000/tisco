package com.tisco.modules.news.dao;

import com.tisco.modules.news.entity.AuditComment;
import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;

@MyBatisDao
public interface AuditCommentDao extends CrudDao<AuditComment> {

}
