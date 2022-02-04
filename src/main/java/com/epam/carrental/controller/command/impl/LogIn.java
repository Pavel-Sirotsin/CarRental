package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import com.epam.carrental.dao.entity.UserRole;
import com.epam.carrental.service.ServiceProvider;
import com.epam.carrental.service.UserAbstractService;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogIn implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(LogIn.class);

    private final UserAbstractService service = ServiceProvider.INSTANCE.getUserImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to log in a user.");
        HttpSession currentSession = handler.getSession();
        UserDTO userSession = (UserDTO) currentSession.getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), userSession: %s", 32, new ObjectMessage(userSession));

        if (userSession != null) {
            currentSession.setAttribute(AttributeManager.AUTHORIZATION_RESULT_ATTRIBUTE, MessageManager.ALREADY_LOGGED_IN);
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {
            String login = handler.getParameter(ParameterManager.USER_LOGIN_PARAMETER);
            String password = handler.getParameter(ParameterManager.USER_PASSWORD_PARAMETER);
            String pageName = handler.getParameter(ParameterManager.PAGE_PARAMETER);

            String language = (String) handler.getSession().getAttribute(AttributeManager.LANGUAGE_ATTRIBUTE);

            UserDTO user = new UserDTO(null);
            user.login = login;
            user.password = password;
            user.role = UserRole.CLIENT;

            try {
                user = service.getByLogin(user);
                logger.debug("line(%d), user: %s", 49, new ObjectMessage(user));

                currentSession.invalidate();
                HttpSession newSession = handler.getSession();
                logger.debug("New session: %b", newSession.isNew());

                newSession.setAttribute(AttributeManager.USER_SESSION_ATTRIBUTE, user);
                newSession.setAttribute(AttributeManager.LANGUAGE_ATTRIBUTE, language);
                newSession.setAttribute(AttributeManager.AUTHORIZATION_RESULT_ATTRIBUTE,
                        MessageManager.SUCCESS_LOG_IN);
                logger.debug("redirect to index page.");
                handler.sendRedirect(PageManager.INDEX_PATH);

            } catch (ServiceException ex) {
                logger.throwing(Level.ERROR, ex);
                handler.setAttribute(AttributeManager.AUTHORIZATION_RESULT_ATTRIBUTE, ex.getMessage());
                handler.forward(PageManager.getPage(pageName));
            }
        }
        logger.traceExit("done.");

    }
}
