package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public class JavascriptResult extends ActionResult {

    private static final Logger logger = LoggerFactory.getLogger(JavascriptResult.class);
    private static final String contentType = "text/javascript;charset=utf-8";
    private String jsText;

    public JavascriptResult(String jsText) {
        this.jsText = jsText;
    }

    @Override
    public void ExecuteResult() {
        PrintWriter writer = null;
        try {
            response.setContentType(contentType);
            writer = response.getWriter();
            writer.write(jsText);
            writer.flush();
        } catch (IOException e) {
            throw new JSimpleException(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
