package com.sky.jSimple.mvc;

import com.sky.jSimple.exception.JSimpleException;

import java.io.*;

public class htmlResult extends ActionResult {

    private String path;

    public htmlResult(String path) {
        this.path = path;
    }

    @Override
    public void ExecuteResult() {
        File f = new File(WebContext.getServletContext().getRealPath(path));
        response.setHeader("Pragma", "no-cache");    // HTTP/1.0 caches might not implement Cache-Control and might only implement Pragma: no-cache

        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("text/html;charset=utf-8");
        if (f.isFile()) {
            InputStream input = null;

            try {
                OutputStream output = response.getOutputStream();
                input = new BufferedInputStream(new FileInputStream(f));
                byte[] buffer = new byte[4096];
                for (; ; ) {
                    int n = input.read(buffer);
                    if (n == (-1))
                        break;
                    output.write(buffer, 0, n);
                }
                output.flush();
            } catch (IOException e) {
                throw new JSimpleException(e);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {

                        throw new JSimpleException(e);
                    }
                }
            }
        }
    }

}
