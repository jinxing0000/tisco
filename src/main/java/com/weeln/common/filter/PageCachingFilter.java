/**
 * Copyright &copy; 2015-2020 <a href="http://www.weeln.org/">Weeln</a> All rights reserved.
 */
package com.weeln.common.filter;

import com.weeln.common.utils.CacheUtils;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * 页面高速缓存过滤器
 * @author weeln
 * @version 2013-8-5
 */
public class PageCachingFilter extends SimplePageCachingFilter {

	@Override
	protected CacheManager getCacheManager() {
		return CacheUtils.getCacheManager();
	}
	
}
