package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;
import com.sky.jSimple.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;


public class JsonResult extends ActionResult {

    private static final Logger logger = LoggerFactory.getLogger(JsonResult.class);

    public JsonResult(Object model) {
        setModel(model);
    }

    private boolean isIE() {
        String useragent = request.getHeader("user-agent");
        return useragent.contains("MSIE");
    }

    public void ExecuteResult() {
        String jsonText = JSONUtil.toJSON(getModel());

        String contentType = "";
        if (isIE()) {
            contentType = "text/html;charset=utf-8";
        } else {
            contentType = "application/json;charset=utf-8";
        }

        response.setContentType(contentType);

        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(jsonText);
            writer.flush();
        } catch (Exception e) {
            throw new JSimpleException(e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}
