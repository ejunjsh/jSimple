package com.sky.jSimple.mvc;

import com.sky.jSimple.bean.BeanAssembly;
import com.sky.jSimple.bean.BeanContainer;
import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.ioc.IocManager;
import com.sky.jSimple.mvc.bean.ControllerBean;
import com.sky.jSimple.mvc.bean.RequestBean;
import com.sky.jSimple.utils.CastUtil;
import com.sky.jSimple.utils.ClassUtil.MissingLVException;
import com.sky.jSimple.utils.WebUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javassist.NotFoundException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DispatcherServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory
			.getLogger(DispatcherServlet.class);

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 初始化相关配置
		ServletContext servletContext = config.getServletContext();

		FreemarkerResult.init(servletContext, Locale.CHINA, 1);
		VelocityResult.init(servletContext);

		try {
			BeanAssembly.assemble();			
			IocManager.execute();
			UrlMapper.excute();
		}
			catch (JSimpleException e) {
			 e.printStackTrace();
			}
		

	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) {

		// 定义一个 JSP 映射标志（默认为映射失败）
		boolean jspMapped = false;
		// 初始化 WebContext
		try {
			WebContext.init(request, response);
		} catch (JSimpleException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// 获取当前请求相关数据
		String currentRequestMethod = request.getMethod();
		String currentRequestPath = WebUtil.getRequestPath(request);

		// 将“/”请求重定向到首页
		if (currentRequestPath.equals("/")) {
			// WebUtil.redirectRequest(homePage, request, response);
		}
		// 去掉当前请求路径末尾的“/”
		if (currentRequestPath.endsWith("/")) {
			currentRequestPath = currentRequestPath.substring(0,
					currentRequestPath.length() - 1);
		}

		ControllerBase foundControllerBean=null;
		try {
			if (!UrlMapper.handleStaticResource()) {

				
				// 获取并遍历 Action 映射
				Map<RequestBean, ControllerBean> controllerMap = UrlMapper
						.getMpper();
				for (Map.Entry<RequestBean, ControllerBean> actionEntry : controllerMap
						.entrySet()) {
					// 从 RequestBean 中获取 Request 相关属性
					RequestBean requestBean = actionEntry.getKey();
					String requestMethod = requestBean.getRequestMethod();
					String requestRegPath = requestBean.getRequestRegPath(); // 正则表达式
					// 获取请求路径匹配器（使用正则表达式匹配请求路径并从中获取相应的请求参数）
					Matcher requestPathMatcher = Pattern
							.compile(requestRegPath)
							.matcher(currentRequestPath);
					// 判断请求方法与请求路径是否同时匹配
					if (requestMethod.equalsIgnoreCase(currentRequestMethod)
							&& requestPathMatcher.matches()) {
						// 获取 ActionBean 及其相关属性
						ControllerBean controllerBean = actionEntry.getValue();
						Class<?> controllerClass = controllerBean
								.getControllerClass();
						Method actionMethod = controllerBean.getActionMethod();
						Class<?>[] paramClasses = actionMethod
								.getParameterTypes();
						// 创建 Action 方法参数列表
						List<Object> actionMethodParamList = new ArrayList<Object>();
						List<String> paramsMap = controllerBean.getParamNames();
						Map<String, String> requestmMap = getRequstParamsMap(request);
						String temp = requestBean.getRequestOriginalPath();
						for (int i = 1; i <= requestPathMatcher.groupCount(); i++) {
							// 获取请求参数
							String valuesString = requestPathMatcher.group(i);
							int index = temp.indexOf("{");
							int last = temp.indexOf("}");
							String keyString = temp.substring(index + 1, last);
							requestmMap.put(keyString, valuesString);
							temp = temp.substring(last + 1);
						}

						for (int k = 0; k < paramsMap.size(); k++) {
							Class<?> paramClass = paramClasses[k];
							String paramName = paramsMap.get(k);
							if (paramClass.isPrimitive()||paramClass==String.class) {
								String string = requestmMap.get(paramName);
								actionMethodParamList.add(castPirmitiveObject(
										paramClass, string));
							} else {
								if (paramClass == File.class) {
									actionMethodParamList
											.add(getFileFromRequest(request,
													paramName));
								} else if (paramClass == Map.class) {
									actionMethodParamList.add(requestmMap);
								} else {
									Object bean;
									try {
										bean = paramClass.newInstance();
										
										DateTimeConverter dtConverter = new DateConverter();  
								        dtConverter.setPattern("yyyy-MM-dd HH:mm:ss");  
								  
								        ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();  
								        convertUtilsBean.deregister(Date.class);  
								        convertUtilsBean.register(dtConverter, Date.class);  
								  
								        BeanUtilsBean beanUtilsBean = new BeanUtilsBean(convertUtilsBean, new PropertyUtilsBean());  
										
								        beanUtilsBean.populate(bean, requestmMap);
									} catch (InstantiationException e) {
										throw new JSimpleException(e);
									} catch (IllegalAccessException e) {
										throw new JSimpleException(e);
									}
									 catch (InvocationTargetException e) {
										 throw new JSimpleException(e);
									}
									actionMethodParamList.add(bean);
								}
							}

						}
						// 从 BeanHelper 中创建 Action 实例
						Object controllerObject = BeanContainer
								.getBean(controllerClass);
						
						foundControllerBean=(ControllerBase)controllerObject;
						// 调用 Action 方法
						ActionResult actionResult;

						actionMethod.setAccessible(true); // 取消类型安全检测（可提高反射性能）
						try {
							actionResult = (ActionResult) actionMethod.invoke(
									controllerObject,
									actionMethodParamList.toArray());
						} catch (IllegalAccessException e) {
							throw new JSimpleException(e);
						} catch (IllegalArgumentException e) {
							throw new JSimpleException(e);
						} catch (InvocationTargetException e) {
							throw new JSimpleException(e);
						}
						if (actionResult != null) {
							actionResult.ExecuteResult();
						}
						
						
						break;
					}

				}
				if (foundControllerBean==null) {
					ControllerBean bean = UrlMapper.getDefaultAction();
					if (bean != null) {
						Method method = bean.getActionMethod();
						Object controllerObject = BeanContainer.getBean(bean
								.getControllerClass());
						foundControllerBean=(ControllerBase)controllerObject;
						method.setAccessible(true);
						ActionResult actionResult;
						try {
							actionResult = (ActionResult) method
									.invoke(controllerObject, null);
						} catch (IllegalAccessException e) {
							throw new JSimpleException(e);
						} catch (IllegalArgumentException e) {
							throw new JSimpleException(e);
						} catch (InvocationTargetException e) {
							throw new JSimpleException(e);
						}
						if (actionResult != null) {
							actionResult.ExecuteResult();
						}

					}
					else {
						new TextResult("not found any action!").ExecuteResult();
					}
				} else {
					new TextResult("not found any action!").ExecuteResult();
				}
			}
		} catch (JSimpleException e) {

				if(foundControllerBean!=null)
				{
					foundControllerBean.onException(e,request,response);
				}
				else {
					new ControllerBase().onException(e,request,response);
				}

			e.printStackTrace();
		} finally {
			// 销毁 DataContext
			WebContext.destroy();
		}
	}

	private Object castPirmitiveObject(Class<?> clazz, String value) {
		if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
			return CastUtil.castInt(value);
		} else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
			return CastUtil.castLong(value);
		} else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
			return CastUtil.castDouble(value);
		} else if (clazz.equals(String.class)) {
			return value;
		} else {
			return null;
		}
	}

	private File getFileFromRequest(HttpServletRequest request, String fieldName) throws JSimpleException
		 {
		try {
			if (WebContext.isFileUpload()) {
				List<FileItem> fileItemList = WebContext.getFileItems();
				for (FileItem item : fileItemList) {
					if (item.getFieldName().equals(fieldName)
							&& !item.isFormField()) {
						File file = new File(item.getName());
						OutputStream os = new FileOutputStream(file);
						InputStream ins = item.getInputStream();
						int bytesRead = 0;
						byte[] buffer = new byte[8192];
						while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
							os.write(buffer, 0, bytesRead);
						}
						os.close();
						ins.close();

						return file;
					}

				}
			}
		} catch (Exception e) {
			throw new JSimpleException(e);
		}
		
		return null;
	}

	private Map<String, String> getRequstParamsMap(HttpServletRequest request) throws JSimpleException
		 {
		Map<String, String> map = null;
		if (WebContext.isFileUpload()) {
			List<FileItem> fileItemList = WebContext.getFileItems();
			for (FileItem item : fileItemList) {
				if (item.isFormField()) {
					String fieldValue;
					try {
						fieldValue = item.getString("UTF-8");
					} catch (UnsupportedEncodingException e) {
						throw new JSimpleException(e);
					}
					if (map == null) {
						map = new HashMap<String, String>();
					}
					map.put(item.getFieldName(), fieldValue);
					return map;
				}
			}
			return null;
		} else {
			return WebUtil.getRequestParamMap(request);
		}
	}
}
