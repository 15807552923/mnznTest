package com.mnzn.ecode.service;


public interface  CommonService<T> {
	
	public T findOne(Integer id);
	
	public T update(T t);

}
