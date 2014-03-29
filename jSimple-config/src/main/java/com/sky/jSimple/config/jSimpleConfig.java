package com.sky.jSimple.config;

import java.util.Properties;

import com.sky.jSimple.utils.PropsUtil;

public class jSimpleConfig {
	  private static final Properties configProps = PropsUtil.loadProps("jSimple.properties");

	    public static String getConfigString(String key) {
	        return PropsUtil.getString(configProps, key);
	    }

	    public static int getConfigNumber(String key) {
	        return PropsUtil.getNumber(configProps, key);
	    }

	    public static boolean getConfigBoolean(String key) {
	        return PropsUtil.getBoolean(configProps, key);
	    }
}
