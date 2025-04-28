package com.xkodxdf.app.servlet;

import com.google.gson.Gson;
import com.xkodxdf.app.VerifiedRequestDataProvider;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

public abstract class BaseServlet extends HttpServlet {

    protected Gson gson;
    protected VerifiedRequestDataProvider verifiedRequestData;

    @Override
    public void init(ServletConfig config) {
        gson = getAttributeFromContext(Gson.class, config);
        verifiedRequestData = getAttributeFromContext(VerifiedRequestDataProvider.class, config);
    }

    protected void setResponse(int statusCode, Object objectToWrite, HttpServletResponse resp) throws IOException {
        resp.setStatus(statusCode);
        Writer writer = resp.getWriter();
        writer.write(gson.toJson(objectToWrite));
    }

    protected <T> T getAttributeFromContext(Class<T> clazz, ServletConfig config) {
        String className = clazz.getSimpleName();
        Object attribute = config.getServletContext().getAttribute(className);
        return clazz.cast(attribute);
    }
}
