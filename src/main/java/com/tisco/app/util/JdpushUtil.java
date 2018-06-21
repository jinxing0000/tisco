package com.tisco.app.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JdpushUtil {
	 protected static final Logger LOG = LoggerFactory.getLogger(JdpushUtil.class);
	 public static final String TITLE = "申通快递";
	    public static final String ALERT = "祝大家新春快乐";
	    public static final String MSG_CONTENT = "申通快递祝新老客户新春快乐";
	    public static final String REGISTRATION_ID = "0900e8d85ef";
	    public static final String TAG = "tag_api";
	    
	    public  static JPushClient jpushClient=null;
	    
	    private String appKey;
	    
	    private String masterSecret;
	    
	    /**
	     * 
	     * @Title 
	     * @Description (构造方法加载参数)
	     * @author 颜金星
	     * @create 2016年8月30日-下午4:06:04
	     * @param appKey
	     * @param masterSecret
	     */
	    public JdpushUtil(String appKey,String masterSecret){
	    	this.appKey=appKey;
	    	this.masterSecret=masterSecret;
	    }
		
		public static void testSendPush(String appKey ,String masterSecret) {
			
			
			
			 jpushClient = new JPushClient(masterSecret, appKey, 3);
			
		    // HttpProxy proxy = new HttpProxy("localhost", 3128);
		    // Can use this https proxy: https://github.com/Exa-Networks/exaproxy
	       
	        
	        // For push, all you need do is to build PushPayload object.
	        //PushPayload payload = buildPushObject_all_all_alert();
			 Collection<String> registrationIds =new ArrayList<String>();
			 registrationIds.add("1104a89792a1ace5dee");
			 Map<String, String > data=new HashMap<String, String>();
			 data.put("dotId", "123123123");
			 //生成推送的内容，这里我们先测试全部推送
//	        PushPayload payload=buildPushObject_ios_audienceMore_messageWithExtras("1104a89792a1ace5dee",data,"工作提醒","济南申通快递网点于2016-08-30完成检查单");
//	        
//	        
//	        try {
//	        	System.out.println(payload.toString());
//	            PushResult result = jpushClient.sendPush(payload);
//	            System.out.println(result+"................................");
//	            
//	            LOG.info("Got result - " + result);
//	            
//	        } catch (APIConnectionException e) {
//	            LOG.error("Connection error. Should retry later. ", e);
//	            
//	        } catch (APIRequestException e) {
//	            LOG.error("Error response from JPush server. Should review and fix it. ", e);
//	            LOG.info("HTTP Status: " + e.getStatus());
//	            LOG.info("Error Code: " + e.getErrorCode());
//	            LOG.info("Error Message: " + e.getErrorMessage());
//	            LOG.info("Msg ID: " + e.getMsgId());
//	        }
		}
		
		public static PushPayload buildPushObject_all_all_alert() {
		    return PushPayload.alertAll(ALERT);
		}
		/**
		 * 
		 * @Title buildPushObject_all_alias_alert
		 * @Description (广播的方式给所有平台，所有用户推送消息，不带有标题的)
		 * @author 颜金星
		 * @create 2016年8月24日-下午2:22:05
		 * @Param @return
		 * @return PushPayload
		 * @throws
		 */
	    public static PushPayload buildPushObject_all_alias_alert() {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())//设置接受的平台
	                .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到
	                .setNotification(Notification.alert(ALERT))
	                .build();
	    }
	    /**
	     * 
	     * @Title buildPushObject_android_tag_alertWithTitle
	     * @Description (广播的方式给安卓平台的所有用户推送消息带有标题)
	     * @author 颜金星
	     * @create 2016年8月24日-下午2:24:14
	     * @Param @return
	     * @return PushPayload
	     * @throws
	     */
	    public static PushPayload buildPushObject_android_tag_alertWithTitle(String alert,String title,Map<String, String> data) {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android())
	                .setAudience(Audience.all())
	                .setNotification(Notification.android(alert, title, data))
	                .build();
	    }
	    
	    public static PushPayload buildPushObject_android_and_ios() {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.tag("tag1"))
	                .setNotification(Notification.newBuilder()
	                		.setAlert("alert content")
	                		.addPlatformNotification(AndroidNotification.newBuilder()
	                				.setTitle("Android Title").build())
	                		.addPlatformNotification(IosNotification.newBuilder()
	                				.incrBadge(1)
	                				.addExtra("extra_key", "extra_value").build())
	                		.build())
	                .build();
	    }
	    
	    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.ios())
	                .setAudience(Audience.tag_and("tag1", "tag_all"))
	                .setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(ALERT)
	                                .setBadge(5)
	                                .setSound("happy")
	                                .addExtra("from", "JPush")
	                                .build())
	                        .build())
	                 .setMessage(Message.content(MSG_CONTENT))
	                 .setOptions(Options.newBuilder()
	                         .setApnsProduction(true)
	                         .build())
	                 .build();
	    }
	    /**
	     * 
	     * @Title buildPushObject_ios_audienceMore_messageWithExtras
	     * @Description (给用户推送消息)
	     * @author 颜金星
	     * @create 2016年8月30日-下午3:44:31
	     * @Param @param registrationId（用户的注册号）
	     * @Param @param data（发送的数据）
	     * @Param @return
	     * @return PushPayload
	     * @throws
	     */
	    public PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String registrationId,Map<String, String> data,String title,String alert) {
	    	return PushPayload.newBuilder()
	                .setPlatform(Platform.android_ios())
	                .setAudience(Audience.registrationId(registrationId))
	                .setNotification(Notification.android(alert, title, data))
	                .build();
	   }
	   
	   public void sendPush(String registrationId,Map<String, String> data,String title,String alert){
		    jpushClient = new JPushClient(masterSecret, appKey, 3);
			 //生成推送的内容，这里我们先测试全部推送
	        PushPayload payload=buildPushObject_ios_audienceMore_messageWithExtras(registrationId,data,title,alert);
	        try {
	        	System.out.println(payload.toString());
	            PushResult result = jpushClient.sendPush(payload);
	            System.out.println(result+"................................");
	            LOG.info("Got result - " + result);
	            
	        } catch (APIConnectionException e) {
	            LOG.error("Connection error. Should retry later. ", e);
	            
	        } catch (APIRequestException e) {
	            LOG.error("Error response from JPush server. Should review and fix it. ", e);
	            LOG.info("HTTP Status: " + e.getStatus());
	            LOG.info("Error Code: " + e.getErrorCode());
	            LOG.info("Error Message: " + e.getErrorMessage());
	            LOG.info("Msg ID: " + e.getMsgId());
	        }
	   }
	   
	   public PushResult sendPushRadioBroadcast(Map<String, String> data,String title,String alert){
		   PushResult result=null;
		    jpushClient = new JPushClient(masterSecret, appKey, 3);
			 //生成推送的内容，这里我们先测试全部推送
	        PushPayload payload=buildPushObject_android_tag_alertWithTitle(alert,title,data);
	        try {
	            result = jpushClient.sendPush(payload);
	            LOG.info("Got result - " + result);
	            
	        } catch (APIConnectionException e) {
	            LOG.error("Connection error. Should retry later. ", e);
	            
	        } catch (APIRequestException e) {
	            LOG.error("Error response from JPush server. Should review and fix it. ", e);
	            LOG.info("HTTP Status: " + e.getStatus());
	            LOG.info("Error Code: " + e.getErrorCode());
	            LOG.info("Error Message: " + e.getErrorMessage());
	            LOG.info("Msg ID: " + e.getMsgId());
	        }
	        return result;
	   }
	    
	   public static void main(String[] args) {
		   Map<String, String> data=new HashMap<String, String>();
       	   data.put("dotId","8bc62d35-d766-4fd4-84d6-c4b9788465a7");
       	   data.put("checkCode", "CHECK-0001");
       	   data.put("jdcycleid", "228");
		   JdpushUtil jdpush=new JdpushUtil("de0e004c153a41742da11bae","891764136151212519ca08df");
       	   jdpush.sendPushRadioBroadcast(data,"工作提醒","济南申通历下分部"+"预计"+"2016-09-02"+"完成"+"快递站点安全检查单"+"");
	}
}
