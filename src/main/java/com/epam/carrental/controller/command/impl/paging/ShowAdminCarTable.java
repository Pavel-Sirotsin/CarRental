package com.epam.carrental.controller.command.impl.paging;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.dao.entity.UserRole;
import com.epam.carrental.service.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowAdminCarTable implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(ShowAdminCarTable.class);

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to forward to the admin car table.");
        UserDTO sessionUser = (UserDTO) handler.
                getSession().
                getAttribute(AttributeManager.USER_SESSION_ATTRIBUTE);
        logger.debug("line(%d), sessionUser: %s", 24, new ObjectMessage(sessionUser));

        if (sessionUser != null && sessionUser.role == UserRole.ADMIN) {
            handler.forward(PageManager.ADMIN_CAR_PAGE_PATH);
        } else {
            handler.sendRedirect(PageManager.INDEX_PATH);
            logger.debug("redirect to index page.");
        }
    }
}
