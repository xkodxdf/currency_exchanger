package com.xkodxdf.app.controller.filter;

import com.google.gson.Gson;
import com.xkodxdf.app.controller.ErrorResponse;
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
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        gson = (Gson) servletContext.getAttribute(Gson.class.getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Throwable t) {
            servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            servletResponse.setContentType("text/html");
            ErrorResponse errorResponse = ExceptionConverter.convertExceptionToErrorResponse(t);
            ((HttpServletResponse) servletResponse).setStatus(errorResponse.getStatusCode());
            servletResponse.getWriter().write(gson.toJson(errorResponse.getMessage()));
        }
    }
}
