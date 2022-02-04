package com.epam.carrental.controller.command.impl.paging;

import com.epam.carrental.controller.HttpServletHandler;
import com.epam.carrental.controller.command.Command;
import com.epam.carrental.controller.command.util.PageManager;

import javax.servlet.ServletException;
import java.io.IOException;

public class ShowIndexPage implements Command {

    @Override
    public void execute(HttpServletHandler handler) throws ServletException, IOException {
        handler.sendRedirect(PageManager.INDEX_PATH);
    }
}
