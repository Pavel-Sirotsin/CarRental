package com.epam.carrental.controller.command.impl.paging;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.PageManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowFAQPage implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(ShowFAQPage.class);

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to forward to the FAQ page.");
        handler.forward(PageManager.FAQ_PAGE_PATH);
        logger.traceExit("done.");
    }

}
