package com.epam.carrental.controller;


import com.epam.carrental.controller.command.CommandMapper;
import com.epam.carrental.dao.exception.DAOException;
import com.epam.carrental.dao.pool.ConnectionPoolImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet(urlPatterns = "/controller")

public class AppController extends HttpServlet {
    private static final Logger logger = LogManager.getFormatterLogger(AppController.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost - ip: %s, locale: %s",
                req.getRemoteAddr(),
                req.getLocale().getDisplayName());
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("doPost - ip: %s, locale: %s",
                req.getRemoteAddr(),
                req.getLocale().getDisplayName());
        processRequest(req, resp);
    }

    @Override
    public void destroy() {
        logger.traceEntry("- try to call destroy method in servlet");
        try {
            ConnectionPoolImpl.INSTANCE.destroyPool();
            ConnectionPoolImpl.INSTANCE.unregisterDrivers();
        } catch (DAOException e) {
            logger.throwing(Level.ERROR, e);
        }
        logger.traceExit("done.");
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        CommandMapper.INSTANCE.callPerformer(new HttpServletHandler() {

            @Override
            public HttpSession getSession() {
                HttpSession session = req.getSession(false);
                return session == null ?
                        req.getSession(true) :
                        session;
            }

            @Override
            public void invalidateCurrentSession() {
                Optional.ofNullable(req.getSession(false)).
                        ifPresent(HttpSession::invalidate);
            }

            @Override
            public String getParameter(String name) {
                logger.traceEntry("- try to get parameter.");
                logger.traceExit(" with -  %s", name);
                return req.getParameter(name);
            }

            @Override
            public Map<String, String[]> getParameterMap() {
                return req.getParameterMap();
            }

            @Override
            public Object getAttribute(String name) {
                logger.traceEntry("- try to get attribute");
                logger.traceExit(" with - %s", name);
                return req.getAttribute(name);
            }

            @Override
            public void setAttribute(String name, Object value) {
                logger.traceEntry("- try to set attribute: " + name);
                logger.traceExit(" with value - %s", new ObjectMessage(value));
                req.setAttribute(name, value);

            }

            @Override
            public void sendRedirect(String page) throws IOException {
                logger.traceEntry("- try to redirect to the page.");
                logger.traceExit(" page is - %s", page);
                resp.sendRedirect(req.getContextPath() + page);
            }

            @Override
            public void forward(String path) throws ServletException, IOException {
                logger.traceEntry("- try to forward to the path.");
                logger.traceExit(" path is - %s", path);
                req.getRequestDispatcher(path).forward(req, resp);
            }

        });
    }


}
