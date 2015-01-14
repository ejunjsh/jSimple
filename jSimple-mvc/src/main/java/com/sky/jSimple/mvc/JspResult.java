package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;

public class JspResult extends ActionResult {

    private static final Logger logger = LoggerFactory.getLogger(JspResult.class);

    private String path;


    public JspResult(String path, Object model) {
        this.path = path;
        this.setModel(model);
    }

    public void ExecuteResult() {
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
