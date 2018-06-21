/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.tisco.modules.news.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tisco.modules.fileupload.entity.FileUpload;
import com.tisco.modules.fileupload.service.FileUploadService;
import com.tisco.modules.news.dao.PictureNewsDao;
import com.tisco.modules.news.entity.PictureNews;
import com.weeln.common.persistence.Page;
import com.weeln.common.service.CrudService;

/**
 * 图片新闻Service
 * @author Ancle
 * @version 2016-10-12
 */
@Service
@Transactional(readOnly = true)
public class PictureNewsService extends CrudService<PictureNewsDao, PictureNews> {
	@Autowired
	private FileUploadService  fileUploadService;

	public PictureNews get(String id) {
		return super.get(id);
	}
	
	public List<PictureNews> findList(PictureNews pictureNews) {
		return super.findList(pictureNews);
	}
	
	public Page<PictureNews> findPage(Page<PictureNews> page, PictureNews pictureNews) {
		return super.findPage(page, pictureNews);
	}
	
	@Transactional(readOnly = false)
	private void saveAttachments(PictureNews pictureNews) {
		// 保存附件
		if (pictureNews.getFileUploads() != null && !pictureNews.getFileUploads().isEmpty()) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			List<FileUpload> fileUploads = pictureNews.getFileUploads();
			for (FileUpload fileUpload : fileUploads) {
				map.put("id",fileUpload.getId());
				map.put("tag",fileUpload.getTag());
				map.put("policyinfoId", pictureNews.getId());
				
				fileUploadService.updateFileLoadBymap(map);
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void save(PictureNews pictureNews) {
		super.save(pictureNews);
		
		saveAttachments(pictureNews);
	}
	
	@Transactional(readOnly = false)
	public void delete(PictureNews pictureNews) {
		super.delete(pictureNews);
	}
	
	public List<FileUpload> findUploadedFiles(String id) {
		return fileUploadService.findFilesByFId(id);
	}
}