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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ShowProfileEditorPage implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(ShowProfileEditorPage.class);

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to forward to the profile editor.");
        HttpSession session = handler.getSession();
        UserDTO sessionUser = (UserDTO) session.getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), sessionUser: %s", 23, new ObjectMessage(sessionUser));

        if (sessionUser == null) {
            logger.debug("redirect to index page.");
            handler.sendRedirect(PageManager.INDEX_PATH);
        } else {
            logger.debug("redirect to the profile editor page.");
            handler.forward(PageManager.PROFILE_EDITOR_PAGE_PATH);
        }
        logger.traceExit("done.");
    }
}
