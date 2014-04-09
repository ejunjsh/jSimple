package com.sky.jSimple.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.exception.JSimpleException;

public class JavascriptResult extends ActionResult {

	private static final Logger logger = LoggerFactory.getLogger(JavascriptResult.class);
	
	@Override
	public void ExecuteResult() throws JSimpleException {
		PrintWriter writer = null;
		try {
			response.setContentType(contentType);
	        writer = response.getWriter();
	        writer.write(jsText);
	        writer.flush();
		} catch (IOException e) {
		    throw new JSimpleException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}
	private static final String contentType = "text/javascript;charset=UTF-8";
	private String jsText;
	
	public JavascriptResult(String jsText) {
		this.jsText = jsText;
	}
}
