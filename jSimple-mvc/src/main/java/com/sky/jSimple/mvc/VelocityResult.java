package com.sky.jSimple.mvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sky.jSimple.exception.JSimpleException;



public class VelocityResult extends ActionResult {

	private static final Logger logger = LoggerFactory.getLogger(VelocityResult.class);
	
	private transient static final String encoding = "utf-8";
	private transient static final String contentType = "text/html;charset=" + encoding;

	private String path;
	
	
	public VelocityResult(String path,Object model) {
		this.path = path;
		setModel(model);
	}
	
	/*
	static {
		String webPath = RenderFactory.getServletContext().getRealPath("/");
		
		Properties properties = new Properties();
		properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, webPath);
		properties.setProperty(Velocity.ENCODING_DEFAULT, encoding); 
		properties.setProperty(Velocity.INPUT_ENCODING, encoding); 
		properties.setProperty(Velocity.OUTPUT_ENCODING, encoding); 
		
		Velocity.init(properties);	// Velocity.init("velocity.properties");	// setup
	}*/
	

	
	public static void  init(ServletContext servletContext)
	{
			String webPath = servletContext.getRealPath("/");
			Properties properties=new Properties();
			properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, webPath);
			properties.setProperty(Velocity.ENCODING_DEFAULT, encoding); 
			properties.setProperty(Velocity.INPUT_ENCODING, encoding); 
			properties.setProperty(Velocity.OUTPUT_ENCODING, encoding);
			Velocity.init(properties);
	}
	
	
	public void ExecuteResult() throws JSimpleException {
		processRequest();
		PrintWriter writer = null;
        try {
            /*
             *  Make a context object and populate with the data.  This
             *  is where the Velocity engine gets the data to resolve the
             *  references (ex. $list) in the template
             */
            VelocityContext context = new VelocityContext();
            
    		// Map root = new HashMap();
    		for (Enumeration<String> attrs=request.getAttributeNames(); attrs.hasMoreElements();) {
    			String attrName = attrs.nextElement();
    			context.put(attrName, request.getAttribute(attrName));
    		}
    		
            /*
             *  get the Template object.  This is the parsed version of your
             *  template input file.  Note that getTemplate() can throw
             *   ResourceNotFoundException : if it doesn't find the template
             *   ParseErrorException : if there is something wrong with the VTL
             *   Exception : if something else goes wrong (this is generally
             *        indicative of as serious problem...)
             */
            Template template = Velocity.getTemplate(path);
            
            /*
             *  Now have the template engine process your template using the
             *  data placed into the context.  Think of it as a  'merge'
             *  of the template and the data to produce the output stream.
             */
           response.setContentType(contentType);
           try {
			writer = response.getWriter();
		} catch (IOException e) {
			throw new JSimpleException(e);
		}	// BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            
           template.merge(context, writer);
           writer.flush();	// flush and cleanup
        }
        catch(ResourceNotFoundException e) {
        	throw new JSimpleException(e);
        }
        finally {
        	if (writer != null)
        		writer.close();
        }
	}

}