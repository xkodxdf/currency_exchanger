package com.xkodxdf.app.controller.servlet;

import com.google.gson.Gson;
import com.xkodxdf.app.controller.VerifiedRequestDataProvider;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public abstract class BaseServlet extends HttpServlet {

    protected boolean baseServletInitialized;
    protected Gson gson;
    protected VerifiedRequestDataProvider verifiedRequestData;

    @Override
    public void init(ServletConfig config) throws ServletException {
        baseServletInitialized = true;
        String gsonName = Gson.class.getSimpleName();
        Object attribute = config.getServletContext().getAttribute(gsonName);
        gson = (Gson) attribute;
        String verifiedRequestDataName = VerifiedRequestDataProvider.class.getSimpleName();
        attribute = config.getServletContext().getAttribute(verifiedRequestDataName);
        verifiedRequestData = (VerifiedRequestDataProvider) attribute;
    }

    protected void setResponse(int statusCode, Object objectToWrite, HttpServletResponse resp) throws IOException {
        resp.setStatus(statusCode);
        resp.getWriter().write(gson.toJson(objectToWrite));
    }
}
