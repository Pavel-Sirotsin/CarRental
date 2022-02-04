package com.epam.carrental.controller.command.impl.paging;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.service.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowUserPage implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(ShowUserPage.class);

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to forward to the user profile page.");
        UserDTO sessionUser = (UserDTO) handler.
                getSession().
                getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), sessionUser: %s", 23, new ObjectMessage(sessionUser));

        if (sessionUser == null) {
            logger.debug("redirect to index page.");
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {
            handler.forward(PageManager.PROFILE_PAGE_PATH);
        }

        logger.traceExit("done.");
    }
}
