package com.epam.carrental.filter;

import com.epam.carrental.controller.command.util.AttributeManager;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(urlPatterns = {"*.jsp"})
public class LocaleFilter implements Filter {
    private static final Logger logger = LogManager.getFormatterLogger(LocaleFilter.class);

    private static final String DEFAULT_LOCALE =
            String.join("_",
                    Locale.getDefault().getLanguage(),
                    Locale.getDefault().getCountry());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.traceEntry("- try to check locale by filter.");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        String language = (String) session.getAttribute(AttributeManager.LANGUAGE_ATTRIBUTE);
        logger.debug("Language: %s", language);

        if(language == null){
            session.setAttribute(AttributeManager.LANGUAGE_ATTRIBUTE, DEFAULT_LOCALE);
        }

        filterChain.doFilter(req, resp);
        logger.traceExit("done.");
    }

    @Override
    public void destroy() {
    }
}
