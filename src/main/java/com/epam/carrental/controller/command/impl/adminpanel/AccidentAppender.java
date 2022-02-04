package com.epam.carrental.controller.command.impl.adminpanel;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.service.AccidentAbstractService;
import com.epam.carrental.service.ServiceProvider;
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

public class AccidentAppender implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(AccidentAppender.class);

    private final AccidentAbstractService accidentServiceImpl = ServiceProvider.INSTANCE.getAccidentImpl();

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to add a new accident from the admin table.");
        HttpSession session = handler.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), sessionUser: %s", 32, new ObjectMessage(sessionUser));

        if (sessionUser == null) {
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {

            Map<String, String[]> params = handler.getParameterMap();
            logger.debug("Map params: %d", params.size());

            try {
                accidentServiceImpl.createByBookingId(params);
                handler.setAttribute(AttributeManager.ADMIN_ACTION_RESULT,
                        MessageManager.SUCCESS_OPERATION);
                logger.debug("go to admin page with result: %s", MessageManager.SUCCESS_OPERATION);
                handler.forward(PageManager.ADMIN_BOOKING_PAGE_PATH);
            } catch (ServiceException ex) {
                logger.throwing(Level.ERROR, ex);
                handler.setAttribute(AttributeManager.ADMIN_ACTION_RESULT, ex.getMessage());
                handler.forward(PageManager.ADMIN_BOOKING_PAGE_PATH);
            }
        }
        logger.traceExit("done.");
    }
}
