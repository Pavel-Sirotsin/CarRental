package com.epam.carrental.controller.command;

import com.epam.carrental.controller.HttpServletHandler;

import javax.servlet.ServletException;
import java.io.IOException;

public interface Command {

    void execute(HttpServletHandler handler) throws ServletException, IOException;
}
