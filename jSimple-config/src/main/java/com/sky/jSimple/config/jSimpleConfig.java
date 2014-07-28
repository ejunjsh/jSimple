package com.sky.jSimple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class jSimpleConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(jSimpleConfig.class);

    public static String configFilePath="/jSimple.xml";

    public static String staticResourceSuffix =".jpg;.bmp;.jpeg;.png;.gif;.html;.css;.js;.htm";

    public static int staticResourceExpire=3600;

    public static String scanPackage="";
}
