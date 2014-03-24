package com.sky.jSimple.mvc;

import java.io.IOException;
import java.io.PrintWriter;

public class JavascriptResult extends ActionResult {

	@Override
	public void ExecuteResult() {
		PrintWriter writer = null;
		try {
			response.setContentType(contentType);
	        writer = response.getWriter();
	        writer.write(jsText);
	        writer.flush();
		} catch (IOException e) {
		
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
