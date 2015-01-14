package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by shaojunjie on 2015/1/7.
 */
public class RedirectResult extends ActionResult {
    private static final Logger logger = LoggerFactory.getLogger(JspResult.class);

    private String path;

    public RedirectResult(String path) {
        this.path = path;
    }

    public void ExecuteResult() {
        try {
            response.sendRedirect(path);
        } catch (IOException e) {
            throw new JSimpleException(e);
        }
    }
}
