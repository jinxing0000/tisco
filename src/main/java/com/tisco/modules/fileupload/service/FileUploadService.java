package com.tisco.modules.fileupload.service;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.weeln.common.service.CrudService;
import com.weeln.common.utils.FileHelper;
import com.weeln.common.utils.IdGen;
import com.tisco.modules.fileupload.dao.FileUploadDao;
import com.tisco.modules.fileupload.entity.FileUpload;

@Service
@Transactional(readOnly = true)
public class FileUploadService extends CrudService<FileUploadDao, FileUpload>{

	/**
	 * 
	 * @param request
	 * @throws IOException
	 * 上传文件 Author:zengfanning
	 */
	@Transactional(readOnly = false)
	public String upload(HttpServletRequest request, FileUpload fileUpload, String cutime){
		String realFileName = "";
		try {
			String uuid = IdGen.uuid();
			// 转型为MultipartHttpRequest
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			// 根据前台的name名称得到上传的文件
			MultipartFile files = multipartRequest.getFile("electronOther");
			// 获得文件名:
			realFileName = files.getOriginalFilename();
			// 获取路径
			String ctxPath = request.getSession().getServletContext().getRealPath("/") + "temp" + File.separator;
			String fileuploadPath = ctxPath;
			// 创建文件
			File dirPath = new File(fileuploadPath);
			if (!dirPath.exists()) {
				dirPath.mkdir();
			}
			File uploadFile = new File(fileuploadPath + realFileName);
			FileCopyUtils.copy(files.getBytes(), uploadFile);
			// 插入文件信息到数据库
			fileUpload.setId(IdGen.uuid());
			fileUpload.setName(files.getOriginalFilename());
			fileUpload.setPath(ctxPath);
			fileUpload.setPolicyinfoId(uuid);
			String stre = files.getOriginalFilename();
			int tag = stre.indexOf(".");
			String subString = stre.substring(tag, stre.length());
			fileUpload.setType(subString);
			dao.insert(fileUpload);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return realFileName;
	}
	
	/**
	 * 修改附件关联的业务表id
	 * @param map
	 */
	public void updateFileLoad(Map<String, Object> map){
		super.dao.updateFileLoad(map);
	}
	
	   /**
	    * 通过主键修改关联表字段id和tag标识位
	    * @param policyinfoid
	    * @return
	    */
	  public void updateFileLoadBymap(Map<String, Object> map){
		  super.dao.updateFileLoadBymap(map);
	  }
	
	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param request
	 */
	public static boolean download(String path, OutputStream os) throws Exception {

		boolean success = true;

		try {
			// 建立文件输入流
			InputStream inputStream = new BufferedInputStream(new FileInputStream(path));

			// 建立文件输出流
			OutputStream outputStream = new BufferedOutputStream(os);

			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
			outputStream.flush();
			outputStream.close();
			inputStream.close();

		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return success;
	}
 

	/**
	 * 保存文件
	 * 
	 * @param attachment
	 * @throws CRUDException
	 */
	@Transactional(readOnly = false)
	public String saveAttachment(HttpServletRequest request, MultipartFile multipartFile){

		//String path = request.getSession().getServletContext().getRealPath("/file") + "\\";
		String path = request.getSession().getServletContext().getRealPath(File.separator + "file") + File.separator;
		FileUpload fileUpload = new FileUpload(); // 创建上传文件对象
		String id = IdGen.uuid();

		// 获取文件名
		String originalFileName = multipartFile.getOriginalFilename();

		// 获取文件的后缀
		String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));

		// 使用UUID生成文件名称
		String logImageName = id + suffix;// 构建文件名称
		// String logImageName = originalFileName;

		// 拼成完整的文件保存路径加文件
		String fileName = path + File.separator + logImageName;

		fileUpload.setId(id); // 设置上传文件的id
		fileUpload.setName(originalFileName); // 设置上传文件的名字
		//fileUpload.setTag(tag);		//设置标识位 以区分，同一个表单有几处上传 附件的情况

		fileUpload.setPath(fileName); // 设置上传文件的路径
		fileUpload.setType(suffix); // 设置上传文件的类型
        fileUpload.setIsNewRecord(true);
		try {
			if (FileHelper.upload(path, multipartFile, id)) {
				super.save(fileUpload);
			}
		} catch (Exception e) {
			 e.printStackTrace();
		}
		return id;
	}

	/**
	 * 删除文件
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteFile(String id) throws Exception {
		try {
			// 根据选择上传文件的id。查询该文件
			FileUpload file = super.get(id);
			// 得到这个文件所在的路径
			FileHelper.deleteFile(file.getPath());
			// 然后再把这个文件删除
			super.delete(file);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
   /**
    * 根据管理的业务表id查询所有的附件
    * @param policyinfoid
    * @return
    */
	public List<FileUpload> findFilesByFId(String policyinfoid) {
       return super.dao.findFilesByFId(policyinfoid);		
	}
	/**
	 * 保存文件信息
	 * @param fileUpload
	 * @return
	 */
	@Transactional(readOnly = false)
	public String insertFile(FileUpload fileUpload){
		fileUpload.setId(IdGen.uuid());
		dao.insert(fileUpload);
		return fileUpload.getId();
	}
	/**
	 * 修改排序号
	 * @param policyInfo
	 */
	@Transactional(readOnly = false)
	public void updateForSort(FileUpload policyInfo){
		dao.updateForSort(policyInfo);
	}
	/**
	 * 修改业务关联字段
	 * @param map
	 */
	@Transactional(readOnly = false)
	public void updatePolicyinfoid(Map<String, Object> map){
		dao.updatePolicyinfoid(map);
	}
	
	 /**
     * 原来的文件解除关联
     * @param map
     */
	@Transactional(readOnly = false)
	public void updatePol(Map<String, Object> map){
		dao.updatePol(map);
	}
}
