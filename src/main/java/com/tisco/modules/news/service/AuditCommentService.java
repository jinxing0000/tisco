package com.tisco.modules.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tisco.modules.news.dao.AuditCommentDao;
import com.tisco.modules.news.entity.AuditComment;
import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;
import com.weeln.common.utils.IdGen;

@Service
@Transactional(readOnly = true)
public class AuditCommentService extends CrudService<AuditCommentDao, AuditComment> {
	@Autowired
	private AuditCommentDao auditCommentDao;
	
	@Transactional(readOnly = false)
	public void save(AuditComment comment) {
		if (comment.getIsNewRecord()) {
			comment.setId(IdGen.uuid());
			
			auditCommentDao.insert(comment);
		} else {
			auditCommentDao.update(comment);
		}
	}
	
	@Transactional(readOnly = true)
	public AuditComment get(String id) {
		return auditCommentDao.get(id);
	}
	
	@Transactional(readOnly = true) 
	public AuditComment get(AuditComment comment) {
		return auditCommentDao.get(comment);
	}
	
	@Transactional(readOnly = false) 
	public void delete(AuditComment comment) {
		auditCommentDao.delete(comment);
	}
	
	@Transactional(readOnly = true)
	public Page<AuditComment> findPage(Page<AuditComment> page, AuditComment comments) {
		return super.findPage(page, comments);
	}
}
