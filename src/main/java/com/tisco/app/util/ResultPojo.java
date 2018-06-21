package com.tisco.app.util;

import com.alibaba.fastjson.JSON;

public class ResultPojo {
	
	private int errcode;
	
	private String errmsg;
	
	private JSON data;

	
	/**
	 * @Title： getErrcode
	 * @Description： (描述)
	 * @author 张鸿
	 * @create 2016年5月26日-下午2:27:03
	 * @return the errcode
	 */
	public int getErrcode() {
		return errcode;
	}

	/**
	 * @Title： setErrcode
	 * @Description： (描述)
	 * @author 张鸿
	 * @create 2016年5月26日-下午2:27:03
	 * @param errcode the errcode to set
	 */
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	/**
	 * @Title： getData
	 * @Description： (描述)
	 * @author 张鸿
	 * @create 2016年5月19日-下午3:34:40
	 * @return the data
	 */
	public JSON getData() {
		return data;
	}

	/**
	 * @Title： setData
	 * @Description： (描述)
	 * @author 张鸿
	 * @create 2016年5月19日-下午3:34:40
	 * @param data the data to set
	 */
	public void setData(JSON data) {
		this.data = data;
	}	
}
