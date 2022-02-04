package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
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
import java.util.Map;

public class ProfileEditor implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(ProfileEditor.class);

    private final UserAbstractService service = ServiceProvider.INSTANCE.getUserImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to edite a user profile info. ");
        HttpSession session = handler.getSession();

        Map<String, String[]> params = handler.getParameterMap();
        logger.debug("Map params: %d", params.size());

        UserDTO sessionUser = (UserDTO) session.getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), sessionUser: %s", 35, new ObjectMessage(sessionUser));

        if (sessionUser == null) {
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {

            try {
                UserDTO cloneUser = (UserDTO) sessionUser.clone();

                sessionUser = service.updatePartly(cloneUser, params);
                session.setAttribute(AttributeManager.USER_SESSION_ATTRIBUTE, sessionUser);
                handler.setAttribute(AttributeManager.EDIT_RESULT_ATTRIBUTE, MessageManager.SUCCESS_OPERATION);
                logger.debug("forward to profile editor page with result: %s", MessageManager.SUCCESS_OPERATION);
                handler.forward(PageManager.PROFILE_EDITOR_PAGE_PATH);
            } catch (ServiceException | CloneNotSupportedException ex) {
                logger.throwing(Level.ERROR, ex);
                handler.setAttribute(AttributeManager.EDIT_RESULT_ATTRIBUTE, ex.getMessage());
                handler.forward(PageManager.PROFILE_EDITOR_PAGE_PATH);
            }

        }
        logger.traceExit("done.");
    }
}
