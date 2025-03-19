package com.xkodxdf.app.controller.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

public abstract class BaseServlet extends HttpServlet {

    protected Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        String gsonName = Gson.class.getSimpleName();
        Object attribute = config.getServletContext().getAttribute(gsonName);
        gson = (Gson) attribute;
    }

    protected void setResponse(int statusCode, Object objectToWrite, HttpServletResponse resp)
            throws IOException {
        resp.setStatus(statusCode);
        try (Writer writer = resp.getWriter()) {
            writer.write(gson.toJson(objectToWrite));
        }
    }
}
