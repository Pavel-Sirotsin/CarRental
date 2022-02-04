package com.epam.carrental.service.validator.impl;

import com.epam.carrental.dao.entity.UserRole;
import com.epam.carrental.service.dto.UserDTO;
import com.epam.carrental.service.exception.ServiceException;
import com.epam.carrental.service.util.MessageManager;
import com.epam.carrental.service.validator.AbstractValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator extends AbstractValidator<UserDTO> {
    private static final Logger logger = LogManager.getFormatterLogger(UserValidator.class);

    private static final String REGEX_LOGIN = "^[\\w_-[а-яА-Я]]{1,20}$";
    private static final String REGEX_PASSWORD = "^[\\w[а-яА-Я]\\p{Punct}]{1,20}$";

    private static final Pattern PATTERN_LOGIN =
            Pattern.compile(REGEX_LOGIN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_PASSWORD =
            Pattern.compile(REGEX_PASSWORD, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    private static final Pattern PATTERN_ROLE =
            Pattern.compile(REGEX_LOGIN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    private Matcher matcher;

    @Override
    public void validate(UserDTO user) throws ServiceException {
        validateLogin(user.login);
        validatePassword(user.password);
        validateRole(user.role);
    }

    public void validateLogin(String login) throws ServiceException {
        logger.traceEntry("- try to validate user login.");
        logger.debug("line(%d), PATTERN_LOGIN: %s", 40, new ObjectMessage(PATTERN_LOGIN));
        matcher = PATTERN_LOGIN.matcher(login);
        logger.debug("line(%d), matcher: %s", 40, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_LOGIN_FORMAT);
            throw new ServiceException(MessageManager.WRONG_LOGIN_FORMAT);
        }

        logger.traceExit("done.");
    }

    public void validatePassword(String password) throws ServiceException {
        logger.traceEntry("- try to validate user password.");
        logger.debug("line(%d), PATTERN_PASSWORD: %s", 55, new ObjectMessage(PATTERN_PASSWORD));
        matcher = PATTERN_PASSWORD.matcher(password);
        logger.debug("line(%d), matcher: %s", 57, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_PASSWORD_FORMAT);
            throw new ServiceException(MessageManager.WRONG_PASSWORD_FORMAT);
        }

        logger.traceExit("done.");
    }

    public void checkPassword(String inputPassword, String basedPassword) throws ServiceException {
        if (!inputPassword.equals(basedPassword)) {
            logger.error("Wrong password!");
            throw new ServiceException(MessageManager.WARNING_LOG_IN);
        }
    }

    public void validateRole(UserRole role) throws ServiceException {
        logger.traceEntry("- try to validate user role.");
        logger.debug("line(%d), PATTERN_ROLE: %s", 78, new ObjectMessage(PATTERN_ROLE));
        matcher = PATTERN_ROLE.matcher(role.name());
        logger.debug("line(%d), matcher: %s", 80, new ObjectMessage(matcher));

        if (!matcher.matches()) {
            logger.warn(MessageManager.WRONG_ROLE_FORMAT);
            throw new ServiceException(MessageManager.WRONG_ROLE_FORMAT);
        }
        logger.traceExit("done.");
    }


}
