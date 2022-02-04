package com.epam.carrental.controller.command.impl;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.AttributeManager;
import com.epam.carrental.controller.command.util.PageManager;
import com.epam.carrental.controller.command.util.ParameterManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LanguageChanger implements Command {
    private static final Logger logger = LogManager.getFormatterLogger(LanguageChanger.class);

    private static final String EN = "en";
    private static final String LOCALE_EN = "en_US";

    private static final String RU = "ru";
    private static final String LOCALE_RU = "ru_RU";

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        logger.traceEntry("- try to change language.");
        String language = handler.getParameter(ParameterManager.LANG_PARAMETER);
        String pageName = handler.getParameter(ParameterManager.PAGE_PARAMETER);

        String pagePath = PageManager.getPage(pageName);
        logger.debug("line(%d), come from %s with language %s", 31, pagePath, language);

        HttpSession session = handler.getSession();
        switch (language) {
            case RU:
                session.setAttribute(AttributeManager.LANGUAGE_ATTRIBUTE, LOCALE_RU);
                break;
            case EN:
                session.setAttribute(AttributeManager.LANGUAGE_ATTRIBUTE, LOCALE_EN);
                break;
        }
        logger.debug("forward to page: %s", pagePath);
        handler.forward(pagePath);
        logger.traceExit("done.");
    }
}
