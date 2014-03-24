package com.sky.jSimple.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javassist.NotFoundException;

import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.mvc.annotation.HttpDelete;
import com.sky.jSimple.mvc.annotation.HttpGet;
import com.sky.jSimple.mvc.annotation.HttpPost;
import com.sky.jSimple.mvc.annotation.HttpPut;
import com.sky.jSimple.mvc.bean.ControllerBean;
import com.sky.jSimple.mvc.bean.RequestBean;
import com.sky.jSimple.utils.ArrayUtil;
import com.sky.jSimple.utils.ClassUtil;
import com.sky.jSimple.utils.ClassUtil.MissingLVException;
import com.sky.jSimple.utils.CollectionUtil;
import com.sky.jSimple.utils.StringUtil;

public class UrlMapper {
	 private static final Map<RequestBean, ControllerBean> urlMapper = new LinkedHashMap<RequestBean, ControllerBean>();

	   public static void excute() throws NotFoundException, MissingLVException 
	   {
	        // 获取所有 Action 类

	        List<Class<?>> controllerClassList = ClassScaner.getClassListBySuper(IController.class);
	        if (CollectionUtil.isNotEmpty(controllerClassList)) {
	            // 定义两个 Action Map

	            Map<RequestBean, ControllerBean> normalUrlMapper = new LinkedHashMap<RequestBean, ControllerBean>(); // 存放普通 Action Map

	            Map<RequestBean, ControllerBean> regexpUrlMapper = new LinkedHashMap<RequestBean, ControllerBean>(); // 存放带有正则表达式的 Action Map

	            // 遍历 Action 类

	            for (Class<?> controllerClass : controllerClassList) {
	                // 获取并遍历该 Action 类中所有的方法（不包括父类中的方法）

	                Method[] actionMethods = controllerClass.getDeclaredMethods();
	                if (ArrayUtil.isNotEmpty(actionMethods)) {
	                    for (Method actionMethod : actionMethods) {
	                        // 判断当前 Action 方法是否带有 @Request 注解
	                         List<String> paramNamesList=new ArrayList<String>();
	                    	 String[] paramNameStrings=ClassUtil.getMethodParamNames(controllerClass, actionMethod.getName());
		                       
		                       for (int i=0;i<paramNameStrings.length;i++) {
								paramNamesList.add(paramNameStrings[i]);
							}
	                       if (actionMethod.isAnnotationPresent(HttpGet.class)) {
	                            String requestPath = actionMethod.getAnnotation(HttpGet.class).value();
	                            putActionMap("GET", requestPath, controllerClass, actionMethod,paramNamesList, normalUrlMapper, regexpUrlMapper);
	                        } else if (actionMethod.isAnnotationPresent(HttpPost.class)) {
	                            String requestPath = actionMethod.getAnnotation(HttpPost.class).value();
	                            putActionMap("POST", requestPath, controllerClass, actionMethod,paramNamesList, normalUrlMapper, regexpUrlMapper);
	                        } else if (actionMethod.isAnnotationPresent(HttpPut.class)) {
	                            String requestPath = actionMethod.getAnnotation(HttpPut.class).value();
	                            putActionMap("PUT", requestPath, controllerClass, actionMethod,paramNamesList, normalUrlMapper, regexpUrlMapper);
	                        } else if (actionMethod.isAnnotationPresent(HttpDelete.class)) {
	                            String requestPath = actionMethod.getAnnotation(HttpDelete.class).value();
	                            putActionMap("DELETE", requestPath, controllerClass, actionMethod,paramNamesList, normalUrlMapper, regexpUrlMapper);
	                        }
	                      
	                    }
	                }
	            }
	            // 初始化最终的 Action Map（将 Common 放在 Regexp 前面）

	            urlMapper.putAll(normalUrlMapper);
	            urlMapper.putAll(regexpUrlMapper);
	        }
	    }

	    private static void putActionMap(String requestMethod, String requestPath, Class<?> controllerClass, Method actionMethod,List<String> paramMap, Map<RequestBean, ControllerBean> commonActionMap, Map<RequestBean, ControllerBean> regexpActionMap) {
	        // 判断 Request Path 中是否带有占位符

	        if (requestPath.matches(".+\\{\\w+\\}.*")) {
	            // 将请求路径中的占位符 {\w+} 转换为正则表达式 (\\w+)
                String regRequestPath=StringUtil.replaceAll(requestPath, "\\{\\w+\\}", "(\\\\w+)");
	            // 将 RequestBean 与 ActionBean 放入 Regexp Action Map 中

	            regexpActionMap.put(new RequestBean(requestMethod, requestPath,regRequestPath), new ControllerBean(controllerClass, actionMethod,paramMap));
	        } else {
	            // 将 RequestBean 与 ActionBean 放入 Common Action Map 中

	            commonActionMap.put(new RequestBean(requestMethod, requestPath,requestPath), new ControllerBean(controllerClass, actionMethod,paramMap));
	        }
	    }

	    public static Map<RequestBean, ControllerBean> getMpper() {
	        return urlMapper;
	    }
}
