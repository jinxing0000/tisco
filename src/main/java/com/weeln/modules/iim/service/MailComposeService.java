/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.modules.iim.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;
import com.weeln.modules.iim.dao.MailBoxDao;
import com.weeln.modules.iim.dao.MailComposeDao;
import com.weeln.modules.iim.entity.MailBox;
import com.weeln.modules.iim.entity.MailCompose;
import com.weeln.modules.iim.entity.MailPage;

/**
 * 发件箱Service
 * @author weeln
 * @version 2015-11-13
 */
@Service
@Transactional(readOnly = true)
public class MailComposeService extends CrudService<MailComposeDao, MailCompose> {
	@Autowired
	private MailComposeDao mailComposeDao;
	public MailCompose get(String id) {
		return super.get(id);
	}
	
	public List<MailCompose> findList(MailCompose mailCompose) {
		return super.findList(mailCompose);
	}
	
	public Page<MailCompose> findPage(MailPage<MailCompose> page, MailCompose mailCompose) {
		return super.findPage(page, mailCompose);
	}
	
	@Transactional(readOnly = false)
	public void save(MailCompose mailCompose) {
		super.save(mailCompose);
	}
	
	@Transactional(readOnly = false)
	public void delete(MailCompose mailCompose) {
		super.delete(mailCompose);
	}

	public int getCount(MailCompose mailCompose) {
		return mailComposeDao.getCount(mailCompose);
	}
	
}