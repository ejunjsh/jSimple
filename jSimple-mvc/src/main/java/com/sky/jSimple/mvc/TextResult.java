package com.sky.jSimple.mvc;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextResult extends ActionResult {
	
	private static final Logger logger = LoggerFactory.getLogger(TextResult.class);
	
    private String text="";
	public TextResult(String text)
	{
	    this.text=text;
	}
	
	public void ExecuteResult() {
		
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
			
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

}