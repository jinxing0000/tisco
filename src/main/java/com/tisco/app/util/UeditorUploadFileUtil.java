/**
 * @Company 航天科技山西公司
 * @Project ueditor1_4_3-utf8-jsp
 * @Package com.casic.ueditor.util
 * @Title UploadFileUtil.java
 * @Description TODO(描述)
 * @author 颜金星
 * @create 2017年3月7日-下午3:01:33
 * @version V 1.0
 */
package com.tisco.app.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import com.baidu.ueditor.ActionEnter;

/**
 * @Company 航天科技山西公司
 * @Project ueditor1_4_3-utf8-jsp
 * @Package com.casic.ueditor.util
 * @ClassName UploadFileUtil.java
 * @Description TODO(描述)
 * @author 颜金星
 * @create 2017年3月7日-下午3:01:33
 */
public class UeditorUploadFileUtil {
      public static String uploadFile(HttpServletRequest request,HttpServletResponse response,String rootPath) throws Exception{
    	System.out.println("执行上传图片servlet+++++=POST");
  		String action=request.getParameter("action");
  		System.out.println("action:"+action);
  		//ServletFileUpload upload = new ServletFileUpload();
  		FileItemStream fileStream = null;
  		 try {
  			DiskFileItemFactory factory = new DiskFileItemFactory();  
  	        ServletFileUpload uploadTim = new ServletFileUpload(factory);
  	        List<FileItem> list = uploadTim.parseRequest(request);  
            for(FileItem item : list){  
            	if(item.getName()==null){
            		continue;
            	}
            	InputStream in=item.getInputStream();
            	byte[] buffer = null;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[10240];
                int n;  
                while ((n = in.read(b)) != -1)
                {  
                    bos.write(b, 0, n);  
                }  
                in.close();
                bos.close();  
                buffer = bos.toByteArray();
                JSONObject jsobject = new JSONObject(); 
                String fname = FTPUtil.getJdFilename();
                //上传图片
                if("uploadimage".equals(action)){
             	   fname=fname +"."+"jpg";
             	   jsobject.put("type", ".jpg");
                }
                //上传视频
                else if("uploadvideo".equals(action)){
             	   fname=fname +"."+"mp4";
             	   jsobject.put("type", ".mp4");
                }
    			   String foldername = "file/"+FTPUtil.getJdgafilename();
                boolean result = FTPUtil.saveinFTP(foldername,fname,buffer);
                String filePath=FTPUtil.getFilePath();
          		jsobject.put("state", "SUCCESS");
          		jsobject.put("url", filePath+"/"+FTPUtil.getJdgafilename()+"/"+fname);
          		jsobject.put("title", fname);
          		jsobject.put("original", item.getName());
          		jsobject.put("size", buffer.length);
            	return jsobject.toString();
// 			FileItemIterator iterator = upload.getItemIterator(request);
// 			while (iterator.hasNext()) {
// 			   fileStream = iterator.next();
//               InputStream in=fileStream.openStream();
//               byte[] buffer = null;
//               ByteArrayOutputStream bos = new ByteArrayOutputStream();
//               byte[] b = new byte[10240];
//               int n;  
//               while ((n = in.read(b)) != -1)
//               {  
//                   bos.write(b, 0, n);  
//               }  
//               in.close();
//               bos.close();  
//               buffer = bos.toByteArray();
//               JSONObject jsobject = new JSONObject(); 
//               String fname = FTPUtil.getJdFilename();
//               //上传图片
//               if("uploadimage".equals(action)){
//            	   fname=fname +"."+"jpg";
//            	   jsobject.put("type", ".jpg");
//               }
//               //上传视频
//               else if("uploadvideo".equals(action)){
//            	   fname=fname +"."+"mp4";
//            	   jsobject.put("type", ".mp4");
//               }
//   			   String foldername = "file/"+FTPUtil.getJdgafilename();
//               boolean result = FTPUtil.saveinFTP(foldername,fname,buffer);
//               String filePath=FTPUtil.getFilePath();
//         		jsobject.put("state", "SUCCESS");
//         		jsobject.put("url", filePath+"/"+FTPUtil.getJdgafilename()+"/"+fname);
//         		jsobject.put("title", fname);
//         		jsobject.put("original", fileStream.getFieldName());
//         		jsobject.put("size", buffer.length);
//           	    return jsobject.toString();
           }
 		} catch (FileUploadException e) {
 			e.printStackTrace();
 		}
    	return null;
      }
}
