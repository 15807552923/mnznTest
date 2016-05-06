package com.mnzn.ecode.common.web;

/**
 * 路径获取接口
 * 
 * @author cxy
 * 
 */
public interface PathResolver {
	public String getPath(String uri);

	public String getPath(String uri, String prefix);
}
