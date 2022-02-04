package com.epam.carrental.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(urlPatterns = {"/*"})
public class EncodingFilter implements Filter {
    private static final Logger logger = LogManager.getFormatterLogger(EncodingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.traceEntry("- try to change encoding to UTF_8 if necessary.");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String codeRequest = request.getCharacterEncoding();
        logger.debug("Request coding: %s", codeRequest);
        String codeResponse = response.getCharacterEncoding();
        logger.debug("Responce coding: %s", codeResponse);

        if (codeRequest == null || codeResponse.equalsIgnoreCase(StandardCharsets.ISO_8859_1.name())) {
            request.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        }

        logger.traceExit("done coding is: %s", response.getCharacterEncoding());
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
