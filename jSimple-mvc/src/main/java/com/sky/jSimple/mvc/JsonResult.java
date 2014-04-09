package com.sky.jSimple.mvc;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.JSONUtil;



public class JsonResult extends ActionResult {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonResult.class);
	
	public JsonResult(Object model)
	{
		setModel(model);
	}

	private boolean isIE()
	{
		String useragent=request.getHeader("user-agent");
		return useragent.contains("MSIE");
	}
	
	public void ExecuteResult() throws JSimpleException {
	    String jsonText=JSONUtil.toJSON(getModel());
	    
	    String contentType="";
	    if(isIE())
	    {
	    	contentType="text/html;charset=UTF-8" ;
	    }
	    else {
			contentType="application/json;charset=UTF-8";
		}

		response.setContentType(contentType);
		
		PrintWriter writer = null;
        try {
			writer = response.getWriter();
			writer.write(jsonText);
			writer.flush();
		} catch (Exception e) {
			throw new JSimpleException(e);
		}
		finally {
			if (writer != null)
				writer.close();
		}
 	}

}
