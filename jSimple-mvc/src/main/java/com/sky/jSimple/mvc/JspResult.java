package com.sky.jSimple.mvc;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.exception.JSimpleException;

public class JspResult extends ActionResult {
	
	private static final Logger logger = LoggerFactory.getLogger(JspResult.class);
	
	private String path;

	
	public JspResult(String path,Object model)
	{
		this.path=path;
		this.setModel(model);
	}
	
	public void ExecuteResult() throws JSimpleException {
		processRequest();
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (ServletException e) {
			throw new JSimpleException(e);
		} catch (IOException e) {
			throw new JSimpleException(e);
		}
	}

}
