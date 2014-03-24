package com.sky.jSimple.mvc.bean;

import java.util.Map;

public class RequestBean {

    private String requestMethod;
    private String requestOriginalPath;
    private String requestRegPath;

    public RequestBean(String requestMethod, String requestOriginalPath,String requestRegPath) {
        this.requestMethod = requestMethod;
        this.requestOriginalPath=requestOriginalPath;
        this.requestRegPath=requestRegPath;
    }

    public String getRequestMethod() {
        return requestMethod;
    }


	public String getRequestRegPath() {
		return requestRegPath;
	}

	public String getRequestOriginalPath() {
		return requestOriginalPath;
	}


}