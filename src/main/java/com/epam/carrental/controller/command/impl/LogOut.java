package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

public class LogOut implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(LogOut.class);

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to log out the user.");
        handler.invalidateCurrentSession();
        handler.getSession();
        handler.sendRedirect(PageManager.INDEX_PATH);
        logger.debug("redirect to index page.");
        logger.traceExit("done.");
    }
}
