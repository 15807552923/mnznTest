package com.mnzn.ecode.common.utils;

import com.alibaba.fastjson.JSONAware;

public class StringAware implements JSONAware {
	
	private String functionString;

    public StringAware(String functionString) {
        this.functionString = functionString;
    }

    @Override
    public String toJSONString() {
        return this.functionString;
    }

}
