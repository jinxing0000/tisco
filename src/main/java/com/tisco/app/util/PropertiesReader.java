package com.tisco.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 
 * @Company 航天科技山西公司
 * @Project jd-1.0v
 * @Package com.jd.util.api
 * @ClassName PropertiesReader.java
 * @Description TODO(描述)
 * @author 张鸿
 * @create 2016年5月23日-下午12:05:06
 */
public class PropertiesReader
{
    private String     file;
    
    private Properties properties;
    
    /** *//**
     * 构造 PropertysReader
     * @param {String} path 相对于classes的文件路径
     */
    public PropertiesReader(String path)
    {
        this.file = path;
        this.properties = new Properties();
    }
    
    /** *//**
     * <p>
     * 本方法根据资源名获取资源内容
     * <p>
     * 
     * @param {String} key 资源文件内key
     * @param {Stirng} defaultValue 默认值
     * 
     * @reaurn String key对应的资源内容
     */
    public synchronized String getProperty(String key)
    {
        try
        {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(this.file);
            
            properties.load(in);
            
        }
        catch (Exception ex1)
        {
            System.out.println("没有找到资源文件:" + this.file);
        }
        return properties.getProperty(key);
    }
    /** *//**
     * <p>
     * 本方法根据资源名获取资源内容
     * <p>
     * 
     * @param {String} key 资源文件内key
     * @param {Stirng} defaultValue 默认值
     * 
     * @reaurn String key对应的资源内容
     */
    public synchronized String getPropertyUTF8(String key)
    {
    	try
    	{
    		InputStream in = this.getClass().getClassLoader()
    				.getResourceAsStream(this.file);
    		BufferedReader bf = new BufferedReader(new InputStreamReader(in, "utf-8"));
    		properties.load(bf);
    		
    	}
    	catch (Exception ex1)
    	{
    		System.out.println("没有找到资源文件:" + this.file);
    	}
    	return properties.getProperty(key);
    }
    
    /** *//**
     * <p>
     * 本方法根据资源名获取资源内容
     * <p>
     * 
     * @param {String} key 资源文件内key
     * @param {Stirng} defaultValue 默认值
     * 
     * @reaurn String key对应的资源内容
     */
    public synchronized String getProperty(String key, String defaultValue)
    {
        try
        {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(this.file);
            
            properties.load(in);
            
        }
        catch (Exception ex1)
        {
            System.out.println("没有找到资源文件:" + this.file);
        }
        return properties.getProperty(key, defaultValue);
    }
    
    /** *//**
     * <p>
     * 本方法根据资源名获取资源内容
     * <p>
     * 
     * @param {String} key 资源文件内key
     * @param {Stirng} defaultValue 默认值
     * @param {boolean} isnull 如果配置文件value为空，是否使用默认值
     * 
     * @reaurn String key对应的资源内容
     */
    public synchronized String getProperty(String key, String defaultValue,boolean isnull)
    {
        String value = null;
        value = getProperty(key,defaultValue);
        if(isnull && (value == null || "".equals(value.trim()) )  )
            value = defaultValue;
        return value;
    }
    
    public static String aaa(){
    	 PropertiesReader preader = new PropertiesReader("config.properties");
         String rootLogger = preader.getProperty("ftp_user_name");
         System.out.println(rootLogger);
    	return rootLogger;
    }
    
    public static void main(String[] args) throws InterruptedException
    {
       for (int i = 0; i < 10; i++) {
		
    	   aaa();
    	   Thread.sleep(5000);
       }
    }
}
