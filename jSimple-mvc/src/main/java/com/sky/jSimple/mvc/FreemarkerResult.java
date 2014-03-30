package com.sky.jSimple.mvc;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerResult extends ActionResult {
	
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerResult.class);
	
	private transient static final String encoding = "utf-8";
	private transient static final String contentType = "text/html; charset=" + encoding;
	private transient static final Configuration config = new Configuration();
	
	private String path;
	
	public FreemarkerResult(String path,Object model) {
		this.path=path;
	    setModel(model);
	}
	
	/**
	 * freemarker can not load freemarker.properies automatically
	 */
	public static Configuration getConfiguration() {
		return config;
	}
	
	/**
	 * Set freemarker's property.
	 * The value of template_update_delay is 5 seconds.
	 * Example: FreeMarkerRender.setProperty("template_update_delay", "1600");
	 */
	public static void setProperty(String propertyName, String propertyValue) {
		try {
			FreemarkerResult.getConfiguration().setSetting(propertyName, propertyValue);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void setProperties(Properties properties) {
		try {
			FreemarkerResult.getConfiguration().setSettings(properties);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		}
	}
	
    static void init(ServletContext servletContext, Locale locale, int template_update_delay) {
        // Initialize the FreeMarker configuration;
        // - Create a configuration instance
        // config = new Configuration();
        // - Templates are stoted in the WEB-INF/templates directory of the Web app.
        config.setServletContextForTemplateLoading(servletContext, "/");	// "WEB-INF/templates"
        // - Set update dealy to 0 for now, to ease debugging and testing.
        //   Higher value should be used in production environment.
        
         config.setTemplateUpdateDelay(template_update_delay);
        
        
        // - Set an error handler that prints errors so they are readable with
        //   a HTML browser.
        // config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
        // - Use beans wrapper (recommmended for most applications)
        config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        // - Set the default charset of the template files
        config.setDefaultEncoding(encoding);		// config.setDefaultEncoding("ISO-8859-1");
        // - Set the charset of the output. This is actually just a hint, that
        //   templates may require for URL encoding and for generating META element
        //   that uses http-equiv="Content-type".
        config.setOutputEncoding(encoding);			// config.setOutputEncoding("UTF-8");
        // - Set the default locale
        config.setLocale(locale /* Locale.CHINA */ );		// config.setLocale(Locale.US);
        config.setLocalizedLookup(false);
        
        // 去掉int型输出时的逗号, 例如: 123,456
        // config.setNumberFormat("#");		// config.setNumberFormat("0"); 也可以
        config.setNumberFormat("#0.#####");
    }

	public void ExecuteResult() {
		processRequest();
		
		response.setContentType(contentType);
        Enumeration<String> attrs = request.getAttributeNames();
		Map root = new HashMap();
		while (attrs.hasMoreElements()) {
			String attrName = attrs.nextElement();
			root.put(attrName, request.getAttribute(attrName));
		}
		
		PrintWriter writer = null;
        try {
			Template template = config.getTemplate(path);
			writer = response.getWriter();
			template.process(root, writer);		// Merge the data-model and the template
		} catch (Exception e) {
			
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

}