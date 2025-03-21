package com.xkodxdf.app.controller;

import com.google.gson.Gson;
import com.xkodxdf.app.ExceptionConverter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter("/*")
public class ExceptionFilter implements Filter {

    private Gson gson;

    @Override
    public void init(FilterConfig filterConfig) {
        String gsonName = Gson.class.getSimpleName();
        Object attribute = filterConfig.getServletContext().getAttribute(gsonName);
        gson = (Gson) attribute;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable t) {
            setErrorResponse(t, (HttpServletResponse) servletResponse);
        }
    }

    private void setErrorResponse(Throwable t, HttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setContentType("text/html");
        ErrorResponse errorResponse = ExceptionConverter.toErrorResponse(t);
        resp.setStatus(errorResponse.getStatusCode());
        resp.getWriter().write(gson.toJson(errorResponse.getMessage()));
    }
}
