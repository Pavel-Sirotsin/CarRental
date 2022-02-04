package com.epam.carrental.filter;

import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.dao.entity.UserRole;
import com.epam.carrental.service.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.DispatcherType;
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

@WebFilter(urlPatterns = {"/WEB-INF/jsp/user/profile.jsp"},
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})

public class AccessAdminFilter implements Filter {
    private static final Logger logger = LogManager.getFormatterLogger(AccessAdminFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        logger.traceEntry("- try to filter admin from users.");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        UserDTO sessionUser = (UserDTO) req.
                getSession().
                getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), sessionUser: %s", 36, new ObjectMessage(sessionUser));

        if (sessionUser.role == UserRole.ADMIN) {
            logger.debug("forward to the admin page.");
            req.getRequestDispatcher(PageManager.ADMIN_USER_PAGE_PATH).forward(req, resp);
        }

        filterChain.doFilter(req, resp);
        logger.traceExit("done.");

    }

    @Override
    public void destroy() {
    }
}
