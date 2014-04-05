package com.sky.jSimple.mvc;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.NotFoundException;

import com.sky.jSimple.bean.ClassScaner;
import com.sky.jSimple.config.jSimpleConfig;
import com.sky.jSimple.mvc.WebContext.Request;
import com.sky.jSimple.mvc.annotation.DefaultAction;
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

import freemarker.core.ReturnInstruction.Return;

public class UrlMapper {
	
	private static final Logger logger = LoggerFactory.getLogger(UrlMapper.class);
	
	 private static final Map<RequestBean, ControllerBean> urlMapper = new LinkedHashMap<RequestBean, ControllerBean>();
     
	 private static ControllerBean defaultAction=null;
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
	                        else if (actionMethod.isAnnotationPresent(DefaultAction.class))
	                        {
	                        	defaultAction=new ControllerBean(controllerClass, actionMethod, null);
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
	    
	    public static ControllerBean getDefaultAction()
	    {
	    	return defaultAction;
	    }
	    
	    public static boolean handleStaticResource() throws IOException
	    {
	    	 HttpServletRequest request= WebContext.getRequest();
	    	  HttpServletResponse response=WebContext.getResponse();
	    	  ServletContext servletContext=WebContext.getServletContext();
	    	    String url = request.getRequestURI();
	            String path = request.getContextPath();
	            url = url.substring(path.length());
	            if (url.toUpperCase().startsWith("/WEB-INF/")) {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND);
	                return true;
	            }
	            int n = url.indexOf('?');
	            if (n!=(-1))
	                url = url.substring(0, n);
	            n = url.indexOf('#');
	            if (n!=(-1))
	                url = url.substring(0, n);

	      String ignoreString=jSimpleConfig.getConfigString("resource.suffix");
	      if(ignoreString.isEmpty())
	      {
	    	  ignoreString=".jpg;.bmp;.jpeg;.png;.gif;.html;.css;.js;.htm";
	      }
	      String[] strings=ignoreString.toUpperCase().split(";");
	      boolean flag=false;
	      for(String string :strings)
	      {
	    	  if(url.toUpperCase().endsWith(string))
	    	  {
	    		  flag=true;
	    		  break;
	    	  }
	      }
	      if(flag)
	      {
	            File f = new File(servletContext.getRealPath(url));
	            if (! f.isFile()) {
	                response.sendError(HttpServletResponse.SC_NOT_FOUND);
	                return true;
	            }
	    	 
	    	  int expires=jSimpleConfig.getConfigNumber("resource.expire");
              String  maxAge="";
	              if (expires>0) {
	                  expires =(int) (expires * 1000L);
	                  maxAge = "max-age=" + expires;
	              }
	              else if (expires<0) {
	            	  expires= (-1);
	              }
	    	  long ifModifiedSince = request.getDateHeader("If-Modified-Since");
	          long lastModified = f.lastModified();
	          if (ifModifiedSince!=(-1) && ifModifiedSince>=lastModified) {
	              response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
	              return true;
	          }
	          response.setDateHeader("Last-Modified", lastModified);
	          response.setContentLength((int)f.length());
	          // set cache:

	          if (expires<0) {
	              response.setHeader("Cache-Control", "no-cache");
	          }
	          else if (expires>0) {
	              response.setHeader("Cache-Control", maxAge);
	              response.setDateHeader("Expires", System.currentTimeMillis() + expires);
	          }

	          String mime = servletContext.getMimeType(f.getName());
	          mime= mime==null ? "application/octet-stream" : mime;
	          response.setContentType(mime);
	          sendFile(f, response.getOutputStream());
	          return true;
	      }
	      else {
			return false;
		}
	    }


	    static void sendFile(File file, OutputStream output) throws IOException {
	        InputStream input = null;
	        try {
	            input = new BufferedInputStream(new FileInputStream(file));
	            byte[] buffer = new byte[4096];
	            for (;;) {
	                int n = input.read(buffer);
	                if (n==(-1))
	                    break;
	                output.write(buffer, 0, n);
	            }
	            output.flush();
	        }
	        finally {
	            if (input!=null) {
	                try {
	                    input.close();
	                }
	                catch (IOException e) {}
	            }
	        }
	    }
}
