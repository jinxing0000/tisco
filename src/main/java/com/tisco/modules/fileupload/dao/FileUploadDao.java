package com.tisco.modules.fileupload.dao;

import java.util.List;
import java.util.Map;

import com.weeln.common.persistence.CrudDao;
import com.weeln.common.persistence.annotation.MyBatisDao;
import com.tisco.modules.fileupload.entity.FileUpload;

/**
 * 文件上传dao接口
 * @author 洪二伟
 * @version 2016-10-3
 */
@MyBatisDao
public interface FileUploadDao extends CrudDao<FileUpload> {
		/**
		 * 通过关联表id批量修改附件
		 * @param map
		 */
	   public void updateFileLoad(Map<String, Object> map);
	   /**
	    * 根据管理的业务表id查询所有的附件
	    * @param policyinfoid
	    * @return
	    */
	   public List<FileUpload> findFilesByFId(String policyinfoid);
	   /**
	    * 通过主键修改关联表字段id和tag标识位
	    * @param policyinfoid
	    * @return
	    */
	   public void updateFileLoadBymap(Map<String, Object> map);
	   /**
	    * 批量处理关联业务字段
	    * @param map
	    */
	   public void updatePolicyinfoid(Map<String, Object> map);
       /**
        * 原来的文件解除关联
        * @param map
        */
	   public void updatePol(Map<String, Object> map);
	/*	// save
		public void savePolicyInfo(FileUpload policyInfo);

		// delete
		public void deletePolicyInfo(List<String> ids);
	   public void updateFileLoad(List<String> listId);
		// toUpdate
		public FileUpload findPolicyInfoByssId(String id);

		// 保存上传文件的信息到数据库
		public void saveFileupload(FileUpload fileUpload);

		// 获取临时关联
		public String getcutime(String cutime);

		// 根据id找到文件信息
		public FileUpload finduploadByssId(String id);

		public void updateFileLoad(List<String> listId);

		public void updateFileLoad(Map<String, Object> map);

		public void deleteFile(String id);

		public List<FileUpload> findFilesByFId(String policyinfoid);*/
	   
	   public void updateForSort(FileUpload policyInfo);
 
}