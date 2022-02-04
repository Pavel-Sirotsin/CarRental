package com.epam.carrental.tag;

import com.epam.carrental.service.exception.ServiceException;

public interface TagCommand {

    String execute(String language, String parameter) throws ServiceException;
}
