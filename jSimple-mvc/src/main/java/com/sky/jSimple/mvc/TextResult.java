package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TextResult extends ActionResult {
	
	private static final Logger logger = LoggerFactory.getLogger(TextResult.class);
	
    private String text="";
	public TextResult(String text)
	{
	    this.text=text;
	}
	
	public void ExecuteResult() throws JSimpleException {
		
		HttpServletResponse response=WebContext.getResponse();
		
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache

	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        
			response.setContentType("text/html;charset=UFT-8");
	        
	        writer.write(text);
	        writer.flush();
		} catch (IOException e) {
			throw new JSimpleException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

}