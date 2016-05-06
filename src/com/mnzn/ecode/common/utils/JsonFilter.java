package com.mnzn.ecode.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

public class JsonFilter {
	 public static StringAware filter(Object obj,String[] fields){
	        if(obj == null){return null;}
	        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(obj.getClass(), fields);
	        
	        return new StringAware(JSON.toJSONString(obj,filter));
	    }
	    
	    public static List<StringAware> listFilter(List<?> objs,String[] fields, SerializerFeature... feature){
	        if(objs == null){return null;}
	        
	        List<StringAware> awareList = new ArrayList<StringAware>();
	    
	        if(objs.size()>0){
	            Class<?> clazz = objs.get(0).getClass();        
	            SimplePropertyPreFilter filter = new SimplePropertyPreFilter(clazz, fields);
	            
	            for(Object obj:objs){
	                //System.out.println((new StringAware(JSON.toJSONString(obj,filter))).toJSONString());
	                awareList.add(new StringAware(JSON.toJSONString(obj,filter, feature)));
	            }
	        }
	        
	        return awareList;

}
}