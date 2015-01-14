package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class FileResult extends ActionResult {

    private static final Logger logger = LoggerFactory.getLogger(FileResult.class);

    private String path;

    public FileResult(String path) {
        this.path = path;
    }

    public void ExecuteResult() {
        String realPath = WebContext.getServletContext().getRealPath(path);

        File file = new File(realPath);

        try {
            response.addHeader("Content-disposition", "attachment; filename=" + new String(file.getName().getBytes("GBK"), "ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            response.addHeader("Content-disposition", "attachment; filename=" + file.getName());
        }

        String contentType = WebContext.getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        response.setContentType(contentType);
        response.setContentLength((int) file.length());
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            for (int n = -1; (n = inputStream.read(buffer)) != -1; ) {
                outputStream.write(buffer, 0, n);
            }
            outputStream.flush();
        } catch (Exception e) {
            throw new JSimpleException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}