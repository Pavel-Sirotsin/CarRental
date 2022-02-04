package com.epam.carrental.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public interface HttpServletHandler {

    HttpSession getSession();

    void invalidateCurrentSession();

    String getParameter(String name);

    Map<String, String[]> getParameterMap();

    void setAttribute(String name, Object value);

    void sendRedirect(String page) throws IOException;

    void forward(String path) throws ServletException, IOException;

    Object getAttribute(String name) throws ServletException, IOException;


}
