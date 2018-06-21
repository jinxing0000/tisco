package com.weeln.common.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.web.multipart.MultipartFile;

public class FileHelper {
	
	/**
	 * 上传文件
	 * @param path
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	public static boolean upload(String path, MultipartFile multipartFile,String id) throws Exception{
        
		boolean success = true;
		
        //根据真实路径创建目录    
        File logoSaveFile = new File(path);     
        if(!logoSaveFile.exists())     
            logoSaveFile.mkdirs();    
        
        String originalFileName = multipartFile.getOriginalFilename();
        
        //获取文件的后缀   
        String suffix = originalFileName.substring(originalFileName.lastIndexOf("."));     
        
        //使用UUID生成文件名称    
      String logImageName = id+ suffix;//构建文件名称     
//        String logImageName = originalFileName;  
        
        //拼成完整的文件保存路径加文件    
        String fileName = path + File.separator + logImageName;          
        
        File file = new File(fileName);          
        
        try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}   
        
        return success;
	}
	
	/**
	 * 下载文件
	 * @param path
	 * @param multipartFile
	 * @return
	 * @throws Exception
	 */
	public static boolean download(String path, OutputStream os) throws Exception{
		
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
	 * 删除文件
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path){
		boolean success = true;
		try {
			File file = new File(path);    
			file.delete();
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		return success;
	}
	
}
