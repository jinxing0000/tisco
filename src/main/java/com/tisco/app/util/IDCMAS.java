package com.tisco.app.util;

import java.util.List;
import java.util.UUID;

import com.mascloud.model.MoModel;
import com.mascloud.model.StatusReportModel;
import com.mascloud.model.SubmitReportModel;
import com.mascloud.sdkclient.Client;

public class IDCMAS {
	
	private static Client client;
	//短信签名
	//private final  static String  AUTOGRAPH="7SBIGYp9";
	private final  static String  AUTOGRAPH="hMBARo5t";
	/**
	 * 发送短信信息
	 * @param phone
	 * @return
	 */
    public static boolean sendSMS(String phone[],String content){
    	boolean flag=false;
    	try {
    		client =  Client.getInstance();
        	boolean isLogin=login();
        	//判断是否登陆成功
        	if(!isLogin){
        		return flag;
        	}
    		// 测试环境IP
    		//client.login("http://112.33.1.13/app/sdk/login", "sdk2", "123","光谷信息");
    		int sendResult = client. sendDSMS (phone,
    				content, "",  5,AUTOGRAPH, UUID.randomUUID().toString(),false);
    		System.out.println("推送结果: " + sendResult);
    		//logout();
    		if(1==sendResult){
    			flag=true;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return flag;
    }
    /**
     * 发送模板短信
     * @param phone
     * @param params
     * @param tempID
     * @return
     */
    public static boolean sendTSMS(String phone[],String[] params,String tempID){
    	boolean flag=false;
    	try {
    		client =  Client.getInstance();
        	boolean isLogin=login();
        	//判断是否登陆成功
        	if(!isLogin){
        		return flag;
        	}
    		// 测试环境IP
    		//client.login("http://112.33.1.13/app/sdk/login", "sdk2", "123","光谷信息");
        	int sendResult =client.sendTSMS (phone,
        			tempID, params,"",  5,AUTOGRAPH, UUID.randomUUID().toString());
    		System.out.println("推送结果: " + sendResult);
    		//logout();
    		if(1==sendResult){
    			flag=true;
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return flag;
    }
    
    /**
     * 登陆SDK
     * @return
     */
    public static boolean login(){
    	boolean flag=false;
    	// 正式环境IP，登录验证URL，用户名，密码，集团客户名称
//		flag=client.login("http://112.33.1.13/app/sdk/login", "test051101", "test12345","云MAS体验07");
		flag=client.login("http://mas.ecloud.10086.cn/app/sdk/login", "tisco", "tisco","山西太钢信息与自动化技术有限公司");
		return flag;
    }
    
    /**
     * 安全推出SDK
     */
    public static void logout(){
    	client.logout();
    	List< MoModel> moList = client.getMO();
    	for (MoModel mo : moList) {
    	    // 业务处理代码
    	}

    	List< SubmitReportModel> subRptList = client.getSubmitReport();
    	for (SubmitReportModel subRpt : subRptList) {
    	    // 业务处理代码
    	}

    	List< StatusReportModel> ssRptList = client.getReport();
    	for (StatusReportModel ssRpt : ssRptList) {
    	    // 业务处理代码
    	}
    }
}
