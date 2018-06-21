/**
 * @Company 航天科技山西公司
 * @Project jd-1.0v
 * @Package com.jd.util.api
 * @Title FTPUtil.java
 * @Description TODO(描述)
 * @author 张鸿
 * @create 2016年5月20日-下午5:40:49
 * @version V 1.0
 */
package com.tisco.app.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
/**
 * @Company 航天科技山西公司
 * @Project jd-1.0v
 * @Package com.jd.util.api
 * @ClassName FTPUtil.java
 * @Description TODO(描述)
 * @author 张鸿
 * @create 2016年5月20日-下午5:40:49
 */
public class FTPUtil {
	
	public static void main(String[] args) throws IOException {
		
		File file = new File("D:/1111.txt");
		
		InputStream in = new FileInputStream(file);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		byte[] a = new byte[1024];
		
		int numBytesRead = 0;
		while ((numBytesRead = in.read(a)) != -1) {
			output.write(a, 0, numBytesRead);
		}
		
        for(int i = 0;i<10; i ++){
        	boolean aasdf = saveinFTP("jdapp"+i+"/123","20160523093850"+i+".txt",output.toByteArray());
        	System.out.println("第"+i+"条 "+aasdf);
        }
	}
	
    /**
     * 注意： 文件名不能用中文！
     * 		文件夹名称以正斜杠结尾！
     * 		如果文件夹不存在会自动创建！
     * @Title saveinFTP
     * @Description (描述)
     * @author 张鸿
     * @create 2016年5月23日-上午9:41:35
     * @Param @param FolderName
     * @Param @param FileName
     * @Param @param data
     * @Param @return
     * @return boolean
     * @throws
     */
	public synchronized static  boolean saveinFTP(String FolderName, String FileName, byte[] data){
		
		boolean result = false ; 
		
		FTPClient ftpClient = new FTPClient();
		   
		ByteArrayInputStream bis = null;  
		//System.err.println("FileName==="+FileName);
		try {
			if (StringUtils.isNotEmpty(FolderName)&&StringUtils.isNotEmpty(FileName)){
				ftpClient.connect(getServerIP(),21);
				if(ftpClient.login(getUserName(), getUserPwd())){
                    String[] a = FolderName.split("/");
					for (int i = 0; i < a.length; i++) {
						if (StringUtils.isNotEmpty(a[i])&&!ftpClient.changeWorkingDirectory(a[i])) { 
							ftpClient.makeDirectory(a[i]);
							ftpClient.changeWorkingDirectory(a[i]);
						}
					}
					bis = new ByteArrayInputStream(data);
					ftpClient.setBufferSize(1024);
                    ftpClient.setDataTimeout(20000);
                    ftpClient.enterLocalPassiveMode();
                    // 设置文件类型(二进制类型)  
                    if (ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)) {  
                    	result = ftpClient.storeFile(FileName, bis);  
                    }
                    bis.close();
				}
			}
		} catch (SocketException e) {
			System.out.println("SocketException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}finally {  
            try {  
                // 关闭连接  
                ftpClient.disconnect();  
            } catch (IOException e) { 
            	System.out.println("IOExceptionEND");
                e.printStackTrace();  
            }  
        }  
		return result;
	}
	/**
	 * 上传到阿里云服务器
	 * @param FolderName
	 * @param FileName
	 * @param data
	 * @return
	 */
    public synchronized static  boolean saveinFTPVideo(String FolderName, String FileName, byte[] data){
		
		boolean result = false ;
		
		FTPClient ftpClient = new FTPClient();
		   
		ByteArrayInputStream bis = null;  
		//System.err.println("FileName==="+FileName);
		try {
			if (StringUtils.isNotEmpty(FolderName)&&StringUtils.isNotEmpty(FileName)){
				ftpClient.connect(getVideoServerIP(),21);
				if(ftpClient.login(getVideoUserName(), getVideoUserPwd())){
                    String[] a = FolderName.split("/");
					for (int i = 0; i < a.length; i++) {
						if (StringUtils.isNotEmpty(a[i])&&!ftpClient.changeWorkingDirectory(a[i])) { 
							ftpClient.makeDirectory(a[i]);
							ftpClient.changeWorkingDirectory(a[i]);
						}
					}
					bis = new ByteArrayInputStream(data);
					ftpClient.setBufferSize(1024);
                    ftpClient.setDataTimeout(20000);
                    ftpClient.enterLocalPassiveMode();
                    // 设置文件类型(二进制类型)  
                    if (ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE)) {  
                    	result = ftpClient.storeFile(FileName, bis);  
                    }
                    bis.close();
				}
			}
		} catch (SocketException e) {
			System.out.println("SocketException");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException");
			e.printStackTrace();
		}finally {  
            try {  
                // 关闭连接  
                ftpClient.disconnect();  
            } catch (IOException e) { 
            	System.out.println("IOExceptionEND");
                e.printStackTrace();  
            }  
        }  
		return result;
	}
	/**
	 * 
	 * @Title uploadFile
	 * @Description (描述)
	 * @author 张鸿
	 * @create 2016年5月26日-下午1:58:38
	 * @Param @param file
	 * @Param @return
	 * @return String
	 * @throws
	 */
	 public static String uploadFile(File file) {  
	        String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成  
	        String PREFIX = "--", LINE_END = "\r\n";  
	        String CONTENT_TYPE = "multipart/form-data"; // 内容类型  
	        String RequestURL = "http://127.0.0.1:8086/api/jd-mb/img.shtml";  
	        try {  
	            URL url = new URL(RequestURL);  
	            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	            conn.setReadTimeout(180000);  
	            conn.setConnectTimeout(180000);  
	            conn.setDoInput(true); // 允许输入流  
	            conn.setDoOutput(true); // 允许输出流  
	            conn.setUseCaches(false); // 不允许使用缓存  
	            conn.setRequestMethod("POST"); // 请求方式  
	            conn.setRequestProperty("Charset", "UTF-8"); // 设置编码  
	            conn.setRequestProperty("connection", "keep-alive");  
	            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="  
	                    + BOUNDARY);  
	            if (file != null) {  
	                /** 
	                 * 当文件不为空，把文件包装并且上传 
	                 */  
	                OutputStream outputSteam = conn.getOutputStream();  
	  
	                DataOutputStream dos = new DataOutputStream(outputSteam);  
	                StringBuffer sb = new StringBuffer();  
	                sb.append(PREFIX);  
	                sb.append(BOUNDARY);  
	                sb.append(LINE_END);  
	                /** 
	                 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件 
	                 * filename是文件的名字，包含后缀名的 比如:abc.png 
	                 */  
	  
	                sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""  
	                        + file.getName() + "\"" + LINE_END);  
	                sb.append("Content-Type: application/octet-stream; charset="  
	                        + "utf-8" + LINE_END);  
	                sb.append(LINE_END);  
	                dos.write(sb.toString().getBytes());  
	                InputStream is = new FileInputStream(file);  
	                byte[] bytes = new byte[1024];  
	                int len = 0;  
	                while ((len = is.read(bytes)) != -1) {  
	                    dos.write(bytes, 0, len);  
	                }  
	                is.close();  
	                dos.write(LINE_END.getBytes());  
	                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)  
	                        .getBytes();  
	                dos.write(end_data);  
	                dos.flush();  
	                /** 
	                 * 获取响应码 200=成功 当响应成功，获取响应的流 
	                 */  
	                int res = conn.getResponseCode();  
	                if (res == 200) {  
	                    return "success";  
	                }  
	            }  
	        } catch (MalformedURLException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return "fail";  
	    }  
	 /**
	  * 
	  * @Title uploadFile1
	  * @Description (描述)
	  * @author 张鸿
	  * @create 2016年5月26日-下午1:58:45
	  * @Param @param file
	  * @Param @return
	  * @return String
	  * @throws
	  */
     public static String uploadFile1(File file) { 
    	 String RequestURL = "http://127.0.0.1:8086/api/jd-mb/img.shtml"; 
         String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成 
         String PREFIX = "--" , LINE_END = "\r\n";   
         String CONTENT_TYPE = "multipart/form-data"; //内容类型  
         int TIME_OUT = 10*10000000; 
         String CHARSET = "utf-8";
         try {  
             URL url = new URL(RequestURL);   
             HttpURLConnection conn = (HttpURLConnection) url.openConnection(); conn.setReadTimeout(TIME_OUT); conn.setConnectTimeout(TIME_OUT);  
             conn.setDoOutput(true); //允许输出流  
             conn.setUseCaches(false); //不允许使用缓存   
             conn.setRequestMethod("POST"); //请求方式   
             conn.setRequestProperty("Charset", CHARSET);   
             //设置编码   
             conn.setRequestProperty("connection", "keep-alive");   
             conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);  
             if(file!=null) {   
                 /** * 当文件不为空，把文件包装并且上传 */  
                 OutputStream outputSteam=conn.getOutputStream();   
                 DataOutputStream dos = new DataOutputStream(outputSteam);   
                 StringBuffer sb = new StringBuffer();   
                 sb.append(PREFIX);   
                 sb.append(BOUNDARY); sb.append(LINE_END);   
                 /**  
                 * 这里重点注意：  
                 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件  
                 * filename是文件的名字，包含后缀名的 比如:abc.png  
                 */   
                 sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""+file.getName()+"\""+LINE_END);  
                 sb.append("Content-Type: application/octet-stream; charset="+CHARSET+LINE_END);   
                 sb.append(LINE_END);   
                 dos.write(sb.toString().getBytes());   
                 InputStream is = new FileInputStream(file);  
                 byte[] bytes = new byte[1024];   
                 int len = 0;   
                 while((len=is.read(bytes))!=-1)   
                 {   
                    dos.write(bytes, 0, len);   
                 }   
                 is.close();   
                 dos.write(LINE_END.getBytes());   
                 byte[] end_data = (PREFIX+BOUNDARY+PREFIX+LINE_END).getBytes();   
                 dos.write(end_data);   
                 dos.flush();  
                 /**  
                 * 获取响应码 200=成功  
                 * 当响应成功，获取响应的流  
                 */   
                 int res = conn.getResponseCode();   
                 if(res==200)   
                 {  
                 return "success";   
                 }  
             }   
         } catch (MalformedURLException e)   
         { e.printStackTrace(); }   
         catch (IOException e)   
         { e.printStackTrace(); }   
         return "fail";   
     }
     
     /**
      * 
      * @Title downloadFile
      * @Description (FTP下载文件)
      * @author 颜金星
      * @create 2016年7月20日-下午12:45:42
      * @Param @param filePath（文件的路径）
      * @Param @return
      * @return boolean
      * @throws
      */
     public static InputStream downloadFile(String filePath){
    	 InputStream result = null ;
 		 FTPClient ftpClient = new FTPClient();
 		 try {
 			 //判断下载的路径不为空
 			if(!StringUtils.isNotEmpty(filePath)){
 				return result;
 			}
 			
 			String str[]=filePath.split("/");
 			String fileName=str[str.length-1];
 			String remoteFile="";
 			for(int i=0;i<str.length-1;i++){
 				remoteFile+=str[i]+"/";
 			}
 			ftpClient.connect(getServerIP());
			if(ftpClient.login(getUserName(), getUserPwd())){
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				//ftpClient.setBufferSize(1024*1024*20);
                ftpClient.setDataTimeout(18000);
                ftpClient.enterLocalPassiveMode();
                ftpClient.changeWorkingDirectory(remoteFile);
                ByteArrayOutputStream os = new ByteArrayOutputStream();  
                ftpClient.retrieveFile(fileName,os);
                byte[] bytes = os.toByteArray();  
                result = new ByteArrayInputStream(bytes); 
                return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {  
            try {  
                // 关闭连接  
                ftpClient.disconnect();  
            } catch (IOException e) { 
            	System.out.println("IOExceptionEND");
                e.printStackTrace();  
            }  
        }  
    	 return result;
     }
     
	/**
	 * 动态文件配置
	 * @Title getUserName
	 * @Description (描述)
	 * @author 张鸿
	 * @create 2016年5月26日-下午2:01:03
	 * @Param @return
	 * @return String
	 * @throws
	 */
	public static String getUserName(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("ftp_user_name", "");
	}
	public static String getUserPwd(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("ftp_user_pwd", "");
	}
	public static String getServerIP(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("ftp_server_ip", "");
	}
	public static String getVideoUserName(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("video_user_name", "");
	}
	public static String getVideoUserPwd(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("video_user_pwd", "");
	}
	public static String getVideoServerIP(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("video_server_ip", "");
	}
	public static String getDirectory(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("ftp_directory", "");
	}
	public static String getFoldername(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("ftp_foldername", "")+new SimpleDateFormat("yyyyMM").format(new Date())+"/"+new SimpleDateFormat("dd").format(new Date());
	}
	public static String getFilename(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return UUID.randomUUID()+preader.getProperty("ftp_filetype", "");
	}
	public static String getJdgafilename(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return "tisco/"+preader.getProperty("ftp_jdgafilename", "")+new SimpleDateFormat("yyyyMM").format(new Date())+"/"+new SimpleDateFormat("dd").format(new Date());
	}
	public static String getFilePath(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("filePath", "");
	}
	public static String getloadFilePath(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("loadFilePath", "");
	}
	public static String getApkPath(){
		PropertiesReader preader = new PropertiesReader("appversion.properties");
		return preader.getProperty("apkPath", "");
	}
	public static String getJdFilename(){
		return UUID.randomUUID().toString();
	}

}
