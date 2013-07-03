package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ErrorTextResult extends ActionResult {
   private Throwable error;
   
   public ErrorTextResult(Throwable e)
   {
	   error=e;
   }
   
   public void ExecuteResult() throws JSimpleException
   {
	   HttpServletResponse response=WebContext.getResponse();
		PrintWriter writer = null;
		try {
			writer = response.getWriter();
			response.setHeader("Pragma", "no-cache");	// HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache

	        response.setHeader("Cache-Control", "no-cache");
	        response.setDateHeader("Expires", 0);
	        
			response.setContentType("text/html;charset=UFT-8");
	        response.setStatus(500);
	        error=findRootCause(error);
	        writer.write(error.getMessage());
	        
	        writer.flush();
		} catch (IOException e) {
			throw new JSimpleException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
   }
   
   private Throwable findRootCause(Throwable e)
   {
	   Throwable cause = e.getCause();

	      return cause == null ? e  : findRootCause(cause);
   }
}
