package com.epam.carrental.controller.command;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.util.ParameterManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

public enum CommandMapper {
    INSTANCE;
    private static final Logger logger = LogManager.getFormatterLogger(CommandMapper.class);
    private static final CommandFactory FACTORY = new CommandFactory();

    public void callPerformer(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to call right performer.");
        String parameter = handler.getParameter(ParameterManager.COMMAND_PARAMETER);

        try {
            FACTORY.getCommand(parameter).execute(handler);
            logger.traceExit("done.");
        } catch (Exception ex) {
            logger.throwing(Level.ERROR, ex);
            logger.debug("go to the error page.");
            FACTORY.getCommand("error").execute(handler);
            throw new ServletException(ex);
        }
    }
}
